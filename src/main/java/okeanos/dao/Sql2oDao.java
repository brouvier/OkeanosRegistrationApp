package okeanos.dao;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

public class Sql2oDao {

	protected final static Logger logger = LoggerFactory.getLogger(Sql2oDao.class);

	private final static String CONNECT_STRING = "jdbc:h2:file:./h2/okeanos;";
	private final static String CONNECT_OPTION = "INIT=create schema if not exists okeanos\\;use okeanos;TRACE_LEVEL_FILE=0;";

	public static Sql2o sql2o;

	static {
		logger.info("Initialisation de Sql20");

		sql2o = new Sql2o(CONNECT_STRING + CONNECT_OPTION, "OkeanosAdmin", "OkeanosAdminPass");

		Flyway flyway = new Flyway();
		flyway.setLocations("filesystem:" + System.getProperty("user.dir") + "/h2");
		flyway.setBaselineOnMigrate(true);
		flyway.setDataSource(sql2o.getDataSource());
		// logger.info("Flyway info");
		// if (flyway.info().all().length == 0) {
		// logger.info("Flyway baseline");
		// flyway.baseline();
		// }

		logger.info("Flyway migrate");
		try {
			flyway.migrate();
		} catch (Exception e) {
			logger.error("Erreur de migration de la base de donnée");
			logger.error(e.getMessage());
			e.printStackTrace();
			flyway.repair();
			flyway.migrate();
		}
	}
}