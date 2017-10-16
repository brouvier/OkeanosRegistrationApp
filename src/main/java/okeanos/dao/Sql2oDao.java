package okeanos.dao;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import okeanos.util.AppProperties;

public class Sql2oDao {

	protected final static Logger logger = LoggerFactory.getLogger(Sql2oDao.class);

	public static Sql2o sql2o;

	static {
		logger.info("Initialisation de Sql20");

		sql2o = new Sql2o(AppProperties.DB_CONNECT_STRING + AppProperties.DB_CONNECT_OPTION,
				AppProperties.getProperties().dbLogin, AppProperties.getProperties().dbPassword);

		Flyway flyway = new Flyway();
		flyway.setLocations(AppProperties.DB_LOCATION);
		flyway.setBaselineOnMigrate(true);
		flyway.setDataSource(sql2o.getDataSource());

		logger.info("Flyway migrate");
		try {
			flyway.migrate();
		} catch (Exception e) {
			logger.error("Erreur de migration de la base de donnée");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}