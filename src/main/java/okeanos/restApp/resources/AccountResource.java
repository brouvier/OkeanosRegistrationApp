package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.AccountDao;
import okeanos.model.Account;
import okeanos.util.AppProperties;

public class AccountResource extends AbstractResource {

	protected static String ressourcePath = AppProperties.API_CONTEXT + "/account";

	public AccountResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(AccountDao.getAllItems(false));
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			// Non implementé pour le moment
			return null;
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(AccountDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(AccountDao.updateItem(new Gson().fromJson(request.body(), Account.class)));
		});
	}

}