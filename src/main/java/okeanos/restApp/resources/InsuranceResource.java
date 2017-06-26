package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
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
			return JsonUtil.toJson(InsuranceDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> InsuranceDao.getItemById(new Long(request.params(":id"))),
				json());

		delete(ressourcePath + "/:id", (request, response) -> InsuranceDao.deleteItem(new Long(request.params(":id"))),
				json());

		post(ressourcePath,
				(request, response) -> InsuranceDao.save(new Gson().fromJson(request.body(), Insurance.class)), json());
	}

}