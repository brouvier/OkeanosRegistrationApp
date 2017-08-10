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
			// Non implementé pour le moment
			return null;
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison infoSaison = AdherentInfoSaisonDao.getItemById(new Long(request.params(":id")));
			if (!SecurityResource.isLoginAndCurrentAccount(request, infoSaison.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(infoSaison);
		});

		get(ressourcePath + "/saison/:saison_id/account/:account_id", (request, response) -> {
			setSecurity(request, response);
			Long accountId = new Long(request.params(":account_id"));
			if (!SecurityResource.isLoginAndCurrentAccount(request, accountId)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(
					AdherentInfoSaisonDao.getIdBySaisonAndAccount(new Long(request.params(":saison_id")), accountId));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoSaisonDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			AdherentInfoSaison item = new Gson().fromJson(request.body(), AdherentInfoSaison.class);
			if (!SecurityResource.isLoginAndCurrentAccount(request, item.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoSaisonDao.save(item));
		});
	}

}