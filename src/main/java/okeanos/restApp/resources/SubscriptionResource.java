package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.SubscriptionDao;
import okeanos.model.Subscription;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class SubscriptionResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/subscription";

	public SubscriptionResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SubscriptionDao.save(new Gson().fromJson(request.body(), Subscription.class)));
		});
	}

}