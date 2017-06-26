package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.model.AdherentInfoSaison;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class AdherentInfoSaisonResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/adherent_info_saison";

	public AdherentInfoSaisonResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoSaisonDao.getAllItems());
		});

		get(ressourcePath + "/:id",
				(request, response) -> AdherentInfoSaisonDao.getItemById(new Long(request.params(":id"))), json());

		delete(ressourcePath + "/:id",
				(request, response) -> AdherentInfoSaisonDao.deleteItem(new Long(request.params(":id"))), json());

		post(ressourcePath, (request, response) -> AdherentInfoSaisonDao
				.save(new Gson().fromJson(request.body(), AdherentInfoSaison.class)), json());
	}

}