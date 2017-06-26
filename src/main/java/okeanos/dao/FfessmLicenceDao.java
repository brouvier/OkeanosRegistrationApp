package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.FfessmLicence;

public class FfessmLicenceDao {

	public static List<FfessmLicence> getAllItems() {
		String sql = "SELECT id, fk_saison_id, label, price, createdOn FROM ffessm_licence";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(FfessmLicence.class);
		}
	}

	public static FfessmLicence newItem(Long fk_saison_id, String label, Double price) {
		String sql = "insert into ffessm_licence (fk_saison_id, label, price) values (:fk_saison_id, :label, :price)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_saison_id", fk_saison_id)
					.addParameter("label", label).addParameter("price", price).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static FfessmLicence getItemById(Long id) {
		String sql = "SELECT id, fk_saison_id, label, price, createdOn FROM ffessm_licence WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(FfessmLicence.class);
		}
	}

	public static FfessmLicence updateItem(FfessmLicence item) {
		String sql = "update ffessm_licence set fk_saison_id = :fk_saison_id, label = :label, price = :price where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(FfessmLicence item) {
		String sql = "delete from ffessm_licence where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
