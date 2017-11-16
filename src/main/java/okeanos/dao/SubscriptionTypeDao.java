package okeanos.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;

import okeanos.model.SubscriptionType;

public class SubscriptionTypeDao {
	private static Logger logger = LoggerFactory.getLogger(SubscriptionTypeDao.class);

	public static List<SubscriptionType> getAllItems() {
		logger.info("Liste des items");
		String sql = "SELECT id, label FROM subscription_type";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(SubscriptionType.class);
		}
	}

	public static SubscriptionType getItemById(Long id) {
		String sql = "SELECT id, label FROM subscription_type WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(SubscriptionType.class);
		}
	}

	public static SubscriptionType save(SubscriptionType item) {

		if (item == null || "".equals(item.getLabel())) {
			logger.error("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode création
			logger.info("Création d'un item : " + item.getLabel());
			String sql = "insert into subscription_type (label) values (:label)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).addParameter("label", item.getLabel())
						.executeUpdate().getKey();
				logger.debug("ID généré : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			logger.info("Mise à jour d'un item : " + item.getLabel());
			String sql = "update subscription_type set label = :label where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		logger.info("Suppression d'un item : " + id);
		String sql = "delete from subscription_type where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
