package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.Insurance;

public class InsuranceDao {

	public static List<Insurance> getAllItems() {
		String sql = "SELECT id, fk_saison_id, label, price, createdOn FROM insurance";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Insurance.class);
		}
	}

	public static Insurance newItem(Long fk_saison_id, String label, Double price) {
		String sql = "insert into insurance (fk_saison_id, label, price) values (:fk_saison_id, :label, :price)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_saison_id", fk_saison_id)
					.addParameter("label", label).addParameter("price", price).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static Insurance getItemById(Long id) {
		String sql = "SELECT id, fk_saison_id, label, price, createdOn FROM insurance WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Insurance.class);
		}
	}

	public static Insurance updateItem(Insurance item) {
		String sql = "update insurance set fk_saison_id = :fk_saison_id, label = :label, price = :price where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(Insurance item) {
		String sql = "delete from insurance where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
