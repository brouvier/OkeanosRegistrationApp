package okeanos.restApp.resources;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import okeanos.dao.SaisonDao;
import okeanos.model.Saison;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class SaisonResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/saison";

	public SaisonResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SaisonDao.getAllItems());
		});

		get(ressourcePath + "/currentSaison", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SaisonDao.getCurrentSaison());
		});

		get(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SaisonDao.getItemById(new Long(request.params(":id"))));
		});

		delete(ressourcePath + "/:id", (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(SaisonDao.deleteItem(new Long(request.params(":id"))));
		});

		post(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			Saison saison = gson.fromJson(request.body(), Saison.class);
			if (!SecurityResource.isAdmin(request)) {
				// Modification non authorisée, on retourne l'objet initial
				return JsonUtil.toJson(SaisonDao.getItemById(saison.getId()));
			}
			return JsonUtil.toJson(SaisonDao.save(saison));
		});
	}

}