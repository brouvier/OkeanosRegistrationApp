package okeanos.restApp.resources;

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

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionTypeDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionTypeDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil
					.toJson(SubscriptionTypeDao.save(new Gson().fromJson(request.body(), SubscriptionType.class)));
		});
	}

}
