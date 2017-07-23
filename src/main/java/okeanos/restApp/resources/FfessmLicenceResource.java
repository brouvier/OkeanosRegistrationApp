package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.FfessmLicenceDao;
import okeanos.model.FfessmLicence;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class FfessmLicenceResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/ffessm_licence";

	public FfessmLicenceResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(FfessmLicenceDao.getAllItems());
		});

		get(ressourcePath + "/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(FfessmLicenceDao.getAllItemsForSaison(new Long(request.params(":saisonId"))));
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(FfessmLicenceDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(FfessmLicenceDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(FfessmLicenceDao.save(new Gson().fromJson(request.body(), FfessmLicence.class)));
		});
	}

}