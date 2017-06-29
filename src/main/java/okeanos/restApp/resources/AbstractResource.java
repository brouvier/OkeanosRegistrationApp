package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.toJson;
import static spark.Spark.exception;

import spark.Request;
import spark.Response;

public abstract class AbstractResource {

	protected void setupEndpoints() {

		exception(Exception.class, (e, request, response) -> {
			response.status(400);
			response.body(toJson(new ResponseError(e)));
		});

		// options(AppProperties.API_CONTEXT + "/*", (request, response) -> {
		//
		// response.header("test", "pipo");
		//
		// return "OK";
		// });

	}

	protected void setSecurity(Request request, Response response) {
		response.header("isLogin", SecurityResource.isLogin(request).toString());
		response.header("isAdmin", SecurityResource.isAdmin(request).toString());
	}

}
