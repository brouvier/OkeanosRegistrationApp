package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.HockeyTeam;

public class HockeyTeamDao {

	public static List<HockeyTeam> getAllItems() {
		String sql = "SELECT id, label, createdOn FROM hockey_team";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(HockeyTeam.class);
		}
	}

	public static HockeyTeam newItem(String label) {
		String sql = "insert into hockey_team (label) values (:label)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", label).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static HockeyTeam getItemById(Long id) {
		String sql = "SELECT id, label, createdOn FROM hockey_team WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(HockeyTeam.class);
		}
	}

	public static HockeyTeam updateItem(HockeyTeam item) {
		String sql = "update hockey_team set label = :label where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(HockeyTeam item) {
		String sql = "delete from hockey_team where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
