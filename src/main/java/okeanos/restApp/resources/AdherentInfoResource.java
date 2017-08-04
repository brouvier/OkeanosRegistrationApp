package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import okeanos.dao.AdherentInfoDao;
import okeanos.model.AdherentInfo;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class AdherentInfoResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/adherent_info";

	public AdherentInfoResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoDao.getAllItems());
		});

		get(ressourcePath + "/:accountId", (request, response) -> {
			setSecurity(request, response);
			Long accountId = new Long(request.params(":accountId"));
			if (!SecurityResource.isLoginAndCurrentAccount(request, accountId)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return gson.toJson(AdherentInfoDao.getItemByAccountId(accountId));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			if (!SecurityResource.isAdmin(request)) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			AdherentInfo info = gson.fromJson(request.body(), AdherentInfo.class);
			if (!SecurityResource.isLoginAndCurrentAccount(request, info.getFk_account_id())) {
				throw new IllegalAccessException("Illegal Access");
			}
			return JsonUtil.toJson(AdherentInfoDao.save(info));
		});
	}

}
