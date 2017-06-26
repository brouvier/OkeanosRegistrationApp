package okeanos.dao;

import org.sql2o.Sql2o;

public class Sql2oDao {

	public static Sql2o sql2o;

	static {
		sql2o = new Sql2o("jdbc:h2:file:./h2/okeanos;INIT=runscript from './h2/init.sql'", "OkeanosAdmin",
				"OkeanosAdminPass");
	}
}