package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.InsuranceDao;
import okeanos.model.Insurance;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class InsuranceResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/insurance";

	public InsuranceResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(InsuranceDao.getAllItems());
		});

		get(ressourcePath + "/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(InsuranceDao.getAllItemsForSaison(new Long(request.params(":saisonId"))));
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(InsuranceDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(InsuranceDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(InsuranceDao.save(new Gson().fromJson(request.body(), Insurance.class)));
		});
	}

}