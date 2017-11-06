package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.io.EofException;

import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import okeanos.dao.AdherentDocumentDao;
import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.dao.HockeyTeamDao;
import okeanos.dao.SaisonDao;
import okeanos.model.AdherentDocument;
import okeanos.model.AdherentInfoSaison;
import okeanos.model.HockeyTeam;
import okeanos.model.Saison;
import okeanos.util.AppProperties;
import spark.Response;

public class HockeyTeamResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/hockey_team";

	@SuppressWarnings("deprecation")
	public HockeyTeamResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(HockeyTeamDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(HockeyTeamDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(HockeyTeamDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(HockeyTeamDao.save(new Gson().fromJson(request.body(), HockeyTeam.class)));
		});

		get(ressourcePath + "/binder_team/:teamId/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);

			// Acces restreint aux administrateurs
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}

			HockeyTeam team = HockeyTeamDao.getItemById(new Long(request.params(":teamId")));
			Saison saison = SaisonDao.getItemById(new Long(request.params(":saisonId")));

			// Contrôle de la validité des paramètres
			if (team.getId() == null || saison.getId() == null) {
				throw new IllegalStateException("Missing Parameter");
			}

			return sendPDFFile(response, createBinderPDF(team.getId(), saison.getId()));
		});
	}

	/**
	 * Construction du classeur d'une equipe en mergeant l'ensemble des documents
	 * des membres de l'équipe
	 * 
	 * @param teamId
	 *            Identifiant de l'équipe dont les documents doivent être extraient
	 * @return Le PDF mergé sous la forme d'un tableau de byte
	 */
	protected byte[] createBinderPDF(Long teamId, Long saisonId) throws IOException, IllegalStateException {
		byte[] bytes = null;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
			PdfMerger merger = new PdfMerger(pdfDoc);

			List<AdherentInfoSaison> teamMember = AdherentInfoSaisonDao.getAllItemsForSaison(saisonId, teamId);
			logger.warn("Nb adherents : {}", teamMember.size());
			logger.warn("Liste des adherents : {}", teamMember);

			if (teamMember == null || teamMember.size() == 0) {
				throw new IllegalStateException("Missing Parameter");
			}

			for (AdherentInfoSaison item : teamMember) {
				// logger.warn("Adherent id {}, Doc id {}", item.getId(),
				// item.getFk_sick_note_id());
				if (item.getFk_certificate_licence_id() != null) {
					mergeDoc(merger, AdherentDocumentDao.getItemById(item.getFk_certificate_licence_id()));
				}
				if (item.getFk_sick_note_id() != null) {
					mergeDoc(merger, AdherentDocumentDao.getItemById(item.getFk_sick_note_id()));
				}
				if (item.getFk_parental_agreement_id() != null) {
					mergeDoc(merger, AdherentDocumentDao.getItemById(item.getFk_parental_agreement_id()));
				}
			}

			// Récupération du pdf mergé
			pdfDoc.close();
			bytes = outputStream.toByteArray();
		}
		return bytes;
	}

	protected void mergeDoc(PdfMerger merger, AdherentDocument doc) throws IOException {
		if ("application/pdf".equals(doc.getFile_type())) {
			try (InputStream inputStream = new ByteArrayInputStream(doc.getData());) {
				PdfDocument srcDoc1 = new PdfDocument(new PdfReader(inputStream));
				merger.merge(srcDoc1, 1, srcDoc1.getNumberOfPages());
				srcDoc1.close();
			}

		}
	}

	/**
	 * Formatage d'un PDF pour l'envoyer dans la réponse du serveur
	 * 
	 * @param bytes
	 *            Le fichier PDF sous forme de tableau de Byte
	 */
	protected HttpServletResponse sendPDFFile(Response response, byte[] bytes) throws IOException {
		response.type("application/pdf");
		response.header("Content-Disposition", "filename=\"ClasseurEquipe.pdf\"");
		response.raw().setContentLengthLong(bytes.length);

		// Construction du flux de réponse
		try (ServletOutputStream out = response.raw().getOutputStream();
				ByteArrayInputStream in = new ByteArrayInputStream(bytes);) {
			IOUtils.copyLarge(in, out);
		} catch (EofException eofe) { // no problem
		}

		return response.raw();
	}

}