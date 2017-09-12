package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.FfessmLicenceDao;
import okeanos.model.FfessmLicence;
import okeanos.util.AppProperties;

public class FfessmLicenceResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/ffessm_licence";

	public FfessmLicenceResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(FfessmLicenceDao.getAllItems());
		});

		get(ressourcePath + "/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(FfessmLicenceDao.getAllItemsForSaison(new Long(request.params(":saisonId"))));
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isLogin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(FfessmLicenceDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(FfessmLicenceDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(FfessmLicenceDao.save(new Gson().fromJson(request.body(), FfessmLicence.class)));
		});
	}

}