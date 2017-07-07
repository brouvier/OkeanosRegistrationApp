package okeanos.restApp.resources;

import static okeanos.util.JsonUtil.json;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

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

        get(ressourcePath + "/:id", (request, response) -> AdherentInfoDao.getItemByAccountId(new Long(request.params(":id"))),
            json());

        delete(ressourcePath + "/:id",
               (request, response) -> AdherentInfoDao.deleteItem(new Long(request.params(":id"))), json());

        post(ressourcePath,
             (request, response) -> AdherentInfoDao.save(new Gson().fromJson(request.body(),
                                                                             AdherentInfo.class),
                                                                             SecurityResource.getCurrentUserId(request)), json());
    }

}
