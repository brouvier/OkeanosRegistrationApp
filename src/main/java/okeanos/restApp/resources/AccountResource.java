package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.AccountDao;
import okeanos.model.Account;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class AccountResource extends AbstractResource {

	protected static String ressourcePath = AppProperties.API_CONTEXT + "/account";

	public AccountResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AccountDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AccountDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AccountDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AccountDao.updateItem(new Gson().fromJson(request.body(), Account.class)));
		});
	}

}