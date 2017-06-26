package okeanos.dao;

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

	public static Saison getItemById(Long id) {
		String sql = "SELECT id, label, start_date, end_date, createdOn FROM saison WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Saison.class);
		}
	}

	public static Saison save(Saison item) {

		if (item == null || "".equals(item.getLabel())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode crÃ©ation
			System.out.println("CrÃ©ation d'un item : " + item.getLabel());
			String sql = "insert into saison (label, start_date, end_date) values (:label, :start_date, :end_date)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", item.getLabel())
						.addParameter("start_date", item.getStart_date()).addParameter("end_date", item.getEnd_date())
						.executeUpdate().getKey();
				System.out.println("ID généré : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise Ã  jour d'un item : " + item.getLabel());
			String sql = "update saison set label = :label, start_date = :start_date, end_date = :end_date where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from saison where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
