package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.DivingTraining;

public class DivingTrainingDao {

	public static List<DivingTraining> getAllItems() {
		String sql = "SELECT id, label, createdOn FROM diving_training";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(DivingTraining.class);
		}
	}

	public static DivingTraining newItem(String label) {
		String sql = "insert into diving_training (label) values (:label)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", label).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static DivingTraining getItemById(Long id) {
		String sql = "SELECT id, label, createdOn FROM diving_training WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(DivingTraining.class);
		}
	}

	public static DivingTraining updateItem(DivingTraining item) {
		String sql = "update diving_training set label = :label where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(DivingTraining item) {
		String sql = "delete from diving_training where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
