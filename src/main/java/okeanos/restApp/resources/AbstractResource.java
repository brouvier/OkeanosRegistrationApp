package okeanos.restApp.resources;

import static spark.Spark.exception;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.io.EofException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;

public abstract class AbstractResource {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

	protected void setupEndpoints() {

		exception(Exception.class, (e, request, response) -> {
			// logger.error("error during request ", e);
			logger.error("error during request : " + e.getMessage());
			response.status(400);
			response.body(gson.toJson(new ResponseError(e)));
		});

	}

	protected void setSecurity(Request request, Response response) {
		response.header("isLogin", SecurityResource.isLogin(request).toString());
		response.header("isAdmin", SecurityResource.isAdmin(request).toString());
		Long curentAccountId = SecurityResource.currentAccountId(request);
		if (curentAccountId != null)
			response.header("curentAccountId", curentAccountId.toString());
		else
			response.header("curentAccountId", "");
	}

	/**
	 * Class structurant un message de retour en forme d'alerte
	 */
	protected class MessageAlertBean {
		public String level;
		public String message;

		/**
		 * @param level
		 *            Bootstrap level alert
		 * @param message
		 *            Message to display
		 */
		public MessageAlertBean(String level, String message) {
			this.level = level;
			this.message = message;
		}
	}

	/**
	 * Formatage d'un PDF pour l'envoyer dans la réponse du serveur
	 * 
	 * @param bytes
	 *            Le fichier PDF sous forme de tableau de Byte
	 * @param pdfName
	 *            Le nom que prendra le fichier (sans extension)
	 */
	protected HttpServletResponse PdfFileResponse(Response response, byte[] bytes, String pdfName) throws IOException {
		response.type("application/pdf");
		response.header("Content-Disposition", "filename=\"" + pdfName + ".pdf\"");
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
