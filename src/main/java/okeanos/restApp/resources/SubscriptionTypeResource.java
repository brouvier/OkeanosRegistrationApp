package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.SubscriptionTypeDao;
import okeanos.model.SubscriptionType;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class SubscriptionTypeResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/subscription_type";

	public SubscriptionTypeResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionTypeDao.getAllItems());
		});

		get(ressourcePath + "/:id",
				(request, response) -> SubscriptionTypeDao.getItemById(new Long(request.params(":id"))), json());

		delete(ressourcePath + "/:id",
				(request, response) -> SubscriptionTypeDao.deleteItem(new Long(request.params(":id"))), json());

		post(ressourcePath, (request, response) -> SubscriptionTypeDao
				.save(new Gson().fromJson(request.body(), SubscriptionType.class)), json());
	}

}