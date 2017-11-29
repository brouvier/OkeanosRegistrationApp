package okeanos.controler;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import okeanos.dao.AdherentInfoDao;
import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.dao.FfessmLicenceDao;
import okeanos.dao.InsuranceDao;
import okeanos.dao.SaisonDao;
import okeanos.dao.SubscriptionDao;
import okeanos.model.AdherentInfo;
import okeanos.model.AdherentInfoSaison;
import okeanos.model.Certificate;
import okeanos.model.FfessmLicence;
import okeanos.model.Saison;
import okeanos.model.Subscription;
import okeanos.util.AppProperties;

public class CertificateControler {
	private static Logger logger = LoggerFactory.getLogger(CertificateControler.class);

	public static final String TARGET = "C:\\Users\\brouvier\\Downloads\\perso";
	public static final String DEST = String.format("%s\\test.pdf", TARGET);

	public static void main(String[] args) throws Exception {

		AdherentInfoSaison infoSaison = AdherentInfoSaisonDao.getItemById(new Long(1));
		logger.debug(infoSaison.toString());

		CertificateControler.generateCertificate(infoSaison);
		logger.debug("Génération terminée");
	}

	public static byte[] generateCertificate(AdherentInfoSaison infoSaison) throws IOException {
		// Construction de l'attesation
		Certificate certif = newCertificat(infoSaison);

		logger.debug("\r\n" + certif.getBody());

		byte[] bytes = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));

			// Conversion de l'attestation html en pdf
			ConverterProperties properties = new ConverterProperties();
			properties.setBaseUri("");
			Document document = HtmlConverter.convertToDocument(certif.getBody(), pdfDoc, properties);

			Paragraph footer = new Paragraph(certif.getFooter()).setTextAlignment(TextAlignment.CENTER);
			footer.setFixedPosition(document.getLeftMargin(), document.getBottomMargin(), footer.getWidth());
			document.add(footer);

			document.close();
			pdfDoc.close();
			// Extraction du pdf sous forme de binaire
			bytes = outputStream.toByteArray();
		}

		return bytes;
	}

	protected static Certificate newCertificat(AdherentInfoSaison infoSaison) {
		if (infoSaison.getId() == null || infoSaison.getFk_account_id() == null || infoSaison.getFk_saison_id() == null
				|| infoSaison.getFk_subscription_id() == null || infoSaison.getFk_ffessm_licence_id() == null) {
			logger.error("Arguments manquants in [" + infoSaison + "]");
			throw new IllegalStateException("Arguments manquants !");
		}

		AdherentInfo adherent = AdherentInfoDao.getItemByAccountId(infoSaison.getFk_account_id());
		Saison saison = SaisonDao.getItemById(infoSaison.getFk_saison_id());
		Subscription subscription = SubscriptionDao.getItemById(infoSaison.getFk_subscription_id());
		FfessmLicence licence = FfessmLicenceDao.getItemById(infoSaison.getFk_ffessm_licence_id());
		Double insurancePrice = infoSaison.getFk_insurance_id() != null
				? InsuranceDao.getItemById(infoSaison.getFk_insurance_id()).getPrice()
				: 0;

		String name = adherent.getFirstname() + " " + adherent.getLastname();
		Double price = subscription.getPrice() + licence.getPrice() + insurancePrice;
		String date = infoSaison.getCreatedOn() != null
				? AppProperties.DATE_FORMATEUR_DAY.format(infoSaison.getCreatedOn())
				: AppProperties.DATE_FORMATEUR_DAY.format(new Date());

		return new Certificate(getTemplateContent(), name, saison.getLabel(), price.toString(), date);
	}

	protected static String getTemplateContent() {
		String template = "";

		logger.debug("TEMPLATE_FILE [" + AppProperties.TEMPLATE_FILE + "]");

		try {
			InputStream in = new FileInputStream(AppProperties.TEMPLATE_FILE);
			template = IOUtils.toString(in, Charset.forName("UTF-8"));
			IOUtils.closeQuietly(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return template;
	}

}
