package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.DivingTraining;

public class DivingTrainingDao {

	public static List<DivingTraining> getAllItems() {
		String sql = "SELECT id, label, createdOn FROM diving_training ORDER BY label";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(DivingTraining.class);
		}
	}

	public static DivingTraining getItemById(Long id) {
		String sql = "SELECT id, label, createdOn FROM diving_training WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(DivingTraining.class);
		}
	}

	public static DivingTraining save(DivingTraining item) {

		if (item == null || "".equals(item.getLabel())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode création
			System.out.println("Création d'un item : " + item.getLabel());
			String sql = "insert into diving_training (label) values (:label)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", item.getLabel())
						.executeUpdate().getKey();
				System.out.println("ID g�n�r� : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise à jour d'un item : " + item.getLabel());
			String sql = "update diving_training set label = :label where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from diving_training where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
