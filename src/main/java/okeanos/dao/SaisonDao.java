package okeanos.dao;

import java.util.Date;
import java.util.List;

import org.sql2o.Connection;

import okeanos.model.Saison;

public class SaisonDao {

	public static List<Saison> getAllItems() {
		String sql = "SELECT id, label, start_date, end_date, createdOn FROM saison";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Saison.class);
		}
	}

	public static Saison newItem(String label, Date start_date, Date end_date) {
		String sql = "insert into saison (label, start_date, end_date) values (:label, :start_date, :end_date)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", label)
					.addParameter("start_date", start_date).addParameter("end_date", end_date).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static Saison getItemById(Long id) {
		String sql = "SELECT id, label, start_date, end_date, createdOn FROM saison WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Saison.class);
		}
	}

	public static Saison updateItem(Saison item) {
		String sql = "update saison set label = :label, start_date = :start_date, end_date = :end_date where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(Saison item) {
		String sql = "delete from saison where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
