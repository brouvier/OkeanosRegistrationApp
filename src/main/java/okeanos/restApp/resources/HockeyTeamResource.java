package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import okeanos.dao.HockeyTeamDao;
import okeanos.model.HockeyTeam;
import okeanos.util.AppProperties;
import okeanos.util.JsonUtil;

public class HockeyTeamResource extends AbstractResource {

	protected String ressourcePath = AppProperties.API_CONTEXT + "/hockey_team";

	public HockeyTeamResource() {
		super.setupEndpoints();

		get(ressourcePath, (request, response) -> {
			setSecurity(request, response);
			return JsonUtil.toJson(HockeyTeamDao.getAllItems());
		});

		get(ressourcePath + "/:id", (request, response) -> HockeyTeamDao.getItemById(new Long(request.params(":id"))),
				json());

		delete(ressourcePath + "/:id", (request, response) -> HockeyTeamDao.deleteItem(new Long(request.params(":id"))),
				json());

		post(ressourcePath,
				(request, response) -> HockeyTeamDao.save(new Gson().fromJson(request.body(), HockeyTeam.class)),
				json());
	}

}