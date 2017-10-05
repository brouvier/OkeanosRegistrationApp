package okeanos.restApp.resources;

import static spark.Spark.exception;

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
			logger.error("error during request ", e);
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

}
