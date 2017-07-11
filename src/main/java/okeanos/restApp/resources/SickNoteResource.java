package okeanos.restApp.resources;

import okeanos.dao.SickNoteDao;
import okeanos.util.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.post;

public class SickNoteResource extends AbstractResource {

    private static Logger logger = LoggerFactory.getLogger(SickNoteResource.class);

    protected String ressourcePath = AppProperties.API_CONTEXT + "/file";

    public SickNoteResource() {
        super.setupEndpoints();

        post(ressourcePath, "multipart/form-data", (request, response) -> {

            int userId = Integer.parseInt(request.queryParams("userid"));
            byte[] bytes = request.bodyAsBytes();

            logger.info("user id {}", userId);
            logger.info("bytes {}", bytes);

            SickNoteDao.save(userId, bytes);

            return "OK";
        });
    }
}
