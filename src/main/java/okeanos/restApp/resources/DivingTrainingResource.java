package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.DivingTrainingDao;
import okeanos.model.DivingTraining;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class DivingTrainingResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/diving_training";

	public DivingTrainingResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(DivingTrainingDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(DivingTrainingDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(DivingTrainingDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(DivingTrainingDao.save(new Gson().fromJson(request.body(), DivingTraining.class)));
		});
	}

}