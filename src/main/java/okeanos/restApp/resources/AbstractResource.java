package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.toJson;
import static spark.Spark.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Request;
import spark.Response;

public abstract class AbstractResource {

	private final Logger logger = LoggerFactory.getLogger(AbstractResource.class);

	protected Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();

	protected void setupEndpoints() {

		exception(Exception.class, (e, request, response) -> {
			logger.error("error during request ", e);
			response.status(400);
			response.body(toJson(new ResponseError(e)));
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

}
