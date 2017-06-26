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

	public static Insurance getItemById(Long id) {
		String sql = "SELECT id, fk_saison_id, label, price, createdOn FROM insurance WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Insurance.class);
		}
	}

	public static Insurance save(Insurance item) {

		if (item == null || "".equals(item.getLabel())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode crÃ©ation
			System.out.println("CrÃ©ation d'un item : " + item.getLabel());
			String sql = "insert into insurance (fk_saison_id, label, price) values (:fk_saison_id, :label, :price)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_saison_id", item.getFk_saison_id())
						.addParameter("label", item.getLabel()).addParameter("price", item.getPrice()).executeUpdate()
						.getKey();
				System.out.println("ID généré : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise Ã  jour d'un item : " + item.getLabel());
			String sql = "update insurance set fk_saison_id = :fk_saison_id, label = :label, price = :price where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from insurance where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
