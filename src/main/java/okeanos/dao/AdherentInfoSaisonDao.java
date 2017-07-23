package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.AdherentInfoSaison;

public class AdherentInfoSaisonDao {

	public static List<AdherentInfoSaison> getAllItems() {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, createdOn FROM adherent_info_saison";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(AdherentInfoSaison.class);
		}
	}

	public static List<AdherentInfoSaison> getAllItemsForSaison(Long saisonId) {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, createdOn FROM adherent_info_saison WHERE fk_saison_id = :saisonId";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("saisonId", saisonId).executeAndFetch(AdherentInfoSaison.class);
		}
	}

	public static AdherentInfoSaison getItemById(Long id) {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, createdOn FROM adherent_info_saison WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(AdherentInfoSaison.class);
		}
	}

	public static Long getIdBySaisonAndAccount(Long saison_id, Long account_id) {
		String sql = "SELECT id FROM adherent_info_saison "
				+ "WHERE fk_saison_id = :saison_id AND fk_account_id = :account_id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("saison_id", saison_id).addParameter("account_id", account_id)
					.executeAndFetchFirst(Long.class);
		}
	}

	public static AdherentInfoSaison newItem(Long fk_account_id, Long fk_saison_id, Long fk_ffessm_licence_id,
			Long fk_subscription_id, Long fk_insurance_id, Boolean picture_authorisation, Long fk_actual_training_id,
			Long fk_training_id, Long fk_team_id) {
		String sql = "insert into adherent_info_saison (fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id) values (:fk_account_id, :fk_saison_id, :fk_ffessm_licence_id, :fk_subscription_id, "
				+ ":fk_insurance_id, :picture_authorisation, :fk_actual_training_id, :fk_training_id, :fk_team_id)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_account_id", fk_account_id)
					.addParameter("fk_saison_id", fk_saison_id)
					.addParameter("fk_ffessm_licence_id", fk_ffessm_licence_id)
					.addParameter("fk_subscription_id", fk_subscription_id)
					.addParameter("fk_insurance_id", fk_insurance_id)
					.addParameter("picture_authorisation", picture_authorisation)
					.addParameter("fk_actual_training_id", fk_actual_training_id)
					.addParameter("fk_training_id", fk_training_id).addParameter("fk_team_id", fk_team_id)
					.executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static AdherentInfoSaison save(AdherentInfoSaison item) {

		if (item == null || "".equals(item.getFk_account_id())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode création
			System.out.println("Création d'un item");
			String sql = "insert into adherent_info_saison (fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
					+ "fk_insurance_id, picture_authorisation, fk_actual_training_id, fk_training_id, "
					+ "fk_team_id) values (:fk_account_id, :fk_saison_id, :fk_ffessm_licence_id, :fk_subscription_id, "
					+ ":fk_insurance_id, :picture_authorisation, :fk_actual_training_id, :fk_training_id, :fk_team_id)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true)
						.addParameter("fk_account_id", item.getFk_account_id())
						.addParameter("fk_saison_id", item.getFk_saison_id())
						.addParameter("fk_ffessm_licence_id", item.getFk_ffessm_licence_id())
						.addParameter("fk_subscription_id", item.getFk_subscription_id())
						.addParameter("fk_insurance_id", item.getFk_insurance_id())
						.addParameter("picture_authorisation", item.getPicture_authorisation())
						.addParameter("fk_actual_training_id", item.getFk_actual_training_id())
						.addParameter("fk_training_id", item.getFk_team_id())
						.addParameter("fk_team_id", item.getFk_team_id()).executeUpdate().getKey();
				System.out.println("ID généré : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise à jour d'un item");
			String sql = "update adherent_info_saison set fk_account_id = :fk_account_id, fk_saison_id = :fk_saison_id, "
					+ "fk_ffessm_licence_id = :fk_ffessm_licence_id, fk_subscription_id = :fk_subscription_id, "
					+ "fk_insurance_id = :fk_insurance_id, picture_authorisation = :picture_authorisation, "
					+ "fk_actual_training_id = :fk_actual_training_id, fk_training_id = :fk_training_id, "
					+ "fk_team_id = :fk_team_id where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from adherent_info_saison where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
