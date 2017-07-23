package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.Subscription;

public class SubscriptionDao {

	public static List<Subscription> getAllItems() {
		String sql = "SELECT id, fk_saison_id, fk_subscription_type_id, label, price, createdOn FROM subscription";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Subscription.class);
		}
	}

	public static List<Subscription> getAllItemsForSaison(Long saisonId) {
		String sql = "SELECT id, fk_saison_id, fk_subscription_type_id, label, price, createdOn FROM subscription WHERE fk_saison_id = :saisonId";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("saisonId", saisonId).executeAndFetch(Subscription.class);
		}
	}

	public static Subscription getItemById(Long id) {
		String sql = "SELECT id, fk_saison_id, fk_subscription_type_id, label, price, createdOn FROM subscription WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Subscription.class);
		}
	}

	public static Subscription save(Subscription item) {

		if (item == null || "".equals(item.getLabel())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode création
			System.out.println("Création d'un item : " + item.getLabel());
			String sql = "insert into subscription (fk_saison_id, fk_subscription_type_id, label, price) values (:fk_saison_id, :fk_subscription_type_id, :label, :price)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_saison_id", item.getFk_saison_id())
						.addParameter("fk_subscription_type_id", item.getFk_subscription_type_id())
						.addParameter("label", item.getLabel()).addParameter("price", item.getPrice()).executeUpdate()
						.getKey();
				System.out.println("ID g�n�r� : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise à jour d'un item : " + item.getLabel());
			String sql = "update subscription set fk_saison_id = :fk_saison_id, fk_subscription_type_id = :fk_subscription_type_id, label = :label, price = :price where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from subscription where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
