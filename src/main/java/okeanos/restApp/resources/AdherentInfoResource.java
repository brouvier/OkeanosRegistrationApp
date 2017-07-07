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
			return JsonUtil.toJson(AdherentInfoDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoDao.getItemByAccountId(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(AdherentInfoDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			AdherentInfo info = gson.fromJson(request.body(), AdherentInfo.class);
			if (!SecurityResource.isAdmin(request)
					&& !SecurityResource.getCurrentUserId(request).equals(info.getFk_account_id())) {
				// Modification non authorisée, on retourne l'objet initial
				return JsonUtil.toJson(AdherentInfoDao.getItemById(info.getId()));
			}
			return JsonUtil.toJson(AdherentInfoDao.save(info));
		});
	}

}
