package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import okeanos.dao.AdherentInfoDao;
import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.model.AdherentInfo;
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

		get(ressourcePath + "/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);

			List<AdherentInfoSaison> list = AdherentInfoSaisonDao
					.getAllItemsForSaison(new Long(request.params(":saisonId")));
			ArrayList<Adherent> res = new ArrayList<Adherent>();
			for (AdherentInfoSaison infoSaison : list) {
				res.add(new Adherent(infoSaison));
			}

			return JsonUtil.toJson(res);
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

	private class Adherent {
		public AdherentInfo info;
		public AdherentInfoSaison infoSaison;

		public Adherent(AdherentInfoSaison infoSaison) {
			this.infoSaison = infoSaison;
			this.info = AdherentInfoDao.getItemByAccountId(infoSaison.getFk_account_id());
		}
	}

}