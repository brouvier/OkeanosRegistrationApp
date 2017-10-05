package okeanos.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oDao {

	protected final static Logger logger = LoggerFactory.getLogger(Sql2oDao.class);

	public static Sql2o sql2o;

	static {
		logger.info("Initialisation de Sql20");

		try (Connection con = new Sql2o("jdbc:h2:file:./h2/okeanos;INIT=runscript from './h2/init.sql'", "OkeanosAdmin",
				"OkeanosAdminPass").open()) {
			con.commit();
			con.close();
		}

		sql2o = new Sql2o("jdbc:h2:file:./h2/okeanos;INIT=use okeanos;", "OkeanosAdmin", "OkeanosAdminPass");
	}
}