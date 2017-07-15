package okeanos.restApp.resources;

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

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoSaisonDao.getItemById(new Long(request.params(":id"))));
		});

		get(ressourcePath + "/saison/:saison_id/account/:account_id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoSaisonDao.getIdBySaisonAndAccount(new Long(request.params(":saison_id")),
					new Long(request.params(":account_id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoSaisonDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil
					.toJson(AdherentInfoSaisonDao.save(new Gson().fromJson(request.body(), AdherentInfoSaison.class)));
		});
	}

}