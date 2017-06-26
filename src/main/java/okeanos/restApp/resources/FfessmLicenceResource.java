package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
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

		get(ressourcePath + "/:id",
				(request, response) -> FfessmLicenceDao.getItemById(new Long(request.params(":id"))), json());

		delete(ressourcePath + "/:id",
				(request, response) -> FfessmLicenceDao.deleteItem(new Long(request.params(":id"))), json());

		post(ressourcePath,
				(request, response) -> FfessmLicenceDao.save(new Gson().fromJson(request.body(), FfessmLicence.class)),
				json());
	}

}