package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import okeanos.dao.AdherentInfoDao;
import okeanos.dao.AdherentInfoSaisonDao;
import okeanos.dao.FfessmLicenceDao;
import okeanos.dao.InsuranceDao;
import okeanos.dao.SubscriptionDao;
import okeanos.model.AdherentInfo;
import okeanos.model.AdherentInfoSaison;
import okeanos.model.FfessmLicence;
import okeanos.model.Insurance;
import okeanos.model.Subscription;
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

		get(ressourcePath + "/saison/:saisonId", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}

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
			// Non implementé pour le moment
			return null;
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

	@SuppressWarnings("unused")
	private class Adherent {
		public final AdherentInfo info;
		public final AdherentInfoSaison infoSaison;
		public Insurance insurance;
		public FfessmLicence licence;
		public Subscription subscription;

		public Adherent(AdherentInfoSaison infoSaison) {
			this.infoSaison = infoSaison;
			this.info = AdherentInfoDao.getItemByAccountId(infoSaison.getFk_account_id());
			if (infoSaison.getFk_insurance_id() != null)
				this.insurance = InsuranceDao.getItemById(infoSaison.getFk_insurance_id());
			if (infoSaison.getFk_ffessm_licence_id() != null)
				this.licence = FfessmLicenceDao.getItemById(infoSaison.getFk_ffessm_licence_id());
			if (infoSaison.getFk_subscription_id() != null)
				this.subscription = SubscriptionDao.getItemById(infoSaison.getFk_subscription_id());
		}

	}

}