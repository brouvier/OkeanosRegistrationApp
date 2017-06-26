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

	public static Subscription newItem(Long fk_saison_id, Long fk_subscription_type_id, String label, Double price) {
		String sql = "insert into subscription (fk_saison_id, fk_subscription_type_id, label, price) values (:fk_saison_id, :fk_subscription_type_id, :label, :price)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_saison_id", fk_saison_id)
					.addParameter("fk_subscription_type_id", fk_subscription_type_id).addParameter("label", label)
					.addParameter("price", price).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static Subscription getItemById(Long id) {
		String sql = "SELECT id, fk_saison_id, fk_subscription_type_id, label, price, createdOn FROM subscription WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Subscription.class);
		}
	}

	public static Subscription updateItem(Subscription item) {
		String sql = "update subscription set fk_saison_id = :fk_saison_id, fk_subscription_type_id = :fk_subscription_type_id, label = :label, price = :price where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(Subscription item) {
		String sql = "delete from subscription where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
