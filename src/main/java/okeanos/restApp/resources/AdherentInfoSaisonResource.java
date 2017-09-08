package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import okeanos.dao.AdherentDocumentDao;
import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.model.AdherentDocument;
import okeanos.model.AdherentInfoSaison;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;
import spark.Request;
import spark.Response;

public class AdherentInfoSaisonResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/adherent_info_saison";
	protected AppProperties properties = AppProperties.getProperties();
	protected String frontPath = properties.frontRestAcces + ":" + properties.restHostPort + "/#/adherentInfoSaison";

	public AdherentInfoSaisonResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			// Non implementé pour le moment
			return null;
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison infoSaison = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));
			if (!SecurityResource.isLoginAndCurrentAccount(request, infoSaison.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(infoSaison);
		});

		get(ressourcePath + "/saison/:saison_id/account/:account_id", (request, response) -> {
			setSecurity(request, response);
			Long accountId = new Long(request.params(":account_id"));
			if (!SecurityResource.isLoginAndCurrentAccount(request, accountId)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(
					AdherentInfoSaisonDao.getIdBySaisonAndAccount(new Long(request.params(":saison_id")), accountId));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoSaisonDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = new Gson().fromJson(request.body(), AdherentInfoSaison.class);
			if (!SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoSaisonDao.save(item));
		});

		post(ressourcePath + "/:id/sick_note", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));
			if (!SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			saveSickNote(request, response, item);
			return true;
		});

		get(ressourcePath + "/:id/sick_note", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));

			// Accès autorisé au propriétaire du document et aux admin
			if (!SecurityResource.isAdmin(request)
					|| !SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}

			// Si le doc existe on l'envoit
			if (item.getFk_sick_note_id() != null) {
				writeAdherentDocument(item.getFk_sick_note_id(), response);
				return response.raw();
			} else
				return "Erreur - Aucun certificat disponible.";
		});

		post(ressourcePath + "/:id/parental_agreement", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));
			if (!SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			saveParentalAgreement(request, response, item);
			return true;
		});

		get(ressourcePath + "/:id/parental_agreement", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));

			// Accès autorisé au propriétaire du document et aux admin
			if (!SecurityResource.isAdmin(request)
					|| !SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}

			// Si le doc existe on l'envoit
			if (item.getFk_sick_note_id() != null) {
				writeAdherentDocument(item.getFk_parental_agreement_id(), response);
				return response.raw();
			} else
				return "Erreur - Aucun accord parental disponible.";
		});
	}

	private void saveSickNote(Request request, Response response, AdherentInfoSaison ais)
			throws IOException, ServletException {
		// Mise à jour du document
		AdherentDocument sickNote = readAdherentDocument(ais.getFk_sick_note_id(), request);
		logger.info("sickNote : {}", sickNote);

		// Mise à jour des infos adhérent
		if (ais.getFk_sick_note_id() == null) {
			ais.setFk_sick_note_id(sickNote.getId());
			AdherentInfoSaisonDao.save(ais);
		}

		response.redirect(frontPath);
	}

	private void saveParentalAgreement(Request request, Response response, AdherentInfoSaison ais)
			throws IOException, ServletException {
		// Mise à jour du document
		AdherentDocument parentalAgreement = readAdherentDocument(ais.getFk_parental_agreement_id(), request);
		logger.info("parentalAgreement : {}", parentalAgreement);

		// Mise à jour des infos adhérent
		if (ais.getFk_parental_agreement_id() == null) {
			ais.setFk_parental_agreement_id(parentalAgreement.getId());
			AdherentInfoSaisonDao.save(ais);
		}

		response.redirect(frontPath);
	}

	private AdherentDocument readAdherentDocument(Long id, Request request) throws IOException, ServletException {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

		if (request.raw().getParts().size() > 0) {
			Part part = request.raw().getParts().iterator().next();
			InputStream is = part.getInputStream();

			AdherentDocument doc = new AdherentDocument(id, part.getHeader("content-type"),
					part.getHeader("content-disposition"), IOUtils.toByteArray(is), null);

			return AdherentDocumentDao.save(doc);
		} else {
			halt(405, "server error : request part is empty !");
			return null;
		}
	}

	private void writeAdherentDocument(Long id, Response response) throws IOException {
		AdherentDocument doc = AdherentDocumentDao.getItemById(id);

		response.type(doc.getFile_type());
		response.header("Content-Disposition", doc.getContent_disposition());

		OutputStream oStream = response.raw().getOutputStream();
		oStream.write(doc.getData());
		oStream.flush();
		oStream.close();
	}

}