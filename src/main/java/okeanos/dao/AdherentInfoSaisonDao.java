package okeanos.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;

import okeanos.model.AdherentInfoSaison;

public class AdherentInfoSaisonDao {

	private static Logger logger = LoggerFactory.getLogger(AdherentInfoSaisonDao.class);

	public static List<AdherentInfoSaison> getAllItems() {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, nead_certificate, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, fk_sick_note_id, fk_parental_agreement_id, validation_start, "
				+ "validation_general_informations, validation_licence, validation_sick_note, "
				+ "validation_parental_agreement, validation_payment_transmitted, validation_payment_cashed, "
				+ "validation_comment FROM adherent_info_saison";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(AdherentInfoSaison.class);
		}
	}

	public static List<AdherentInfoSaison> getAllItemsForSaison(Long saisonId) {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, nead_certificate, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, fk_sick_note_id, fk_parental_agreement_id, validation_start, validation_general_informations, "
				+ "validation_licence, validation_sick_note, validation_parental_agreement, validation_payment_transmitted, "
				+ "validation_payment_cashed, validation_comment FROM adherent_info_saison WHERE fk_saison_id = :saisonId ORDER BY id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("saisonId", saisonId).executeAndFetch(AdherentInfoSaison.class);
		}
	}

	public static AdherentInfoSaison getItemById(Long id) {
		String sql = "SELECT id, fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
				+ "fk_insurance_id, picture_authorisation, nead_certificate, fk_actual_training_id, fk_training_id, "
				+ "fk_team_id, fk_sick_note_id, fk_parental_agreement_id, validation_start, "
				+ "validation_general_informations, validation_licence, validation_sick_note, "
				+ "validation_parental_agreement, validation_payment_transmitted, validation_payment_cashed, "
				+ "validation_comment FROM adherent_info_saison WHERE id = :id";

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

	public static AdherentInfoSaison save(AdherentInfoSaison item) {

		if (item == null || "".equals(item.getFk_account_id().toString())) {
			logger.error("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Pas d'ID envoyé par le front, on contrôle tout de même qu'il n'existe pas
									// dans la base
			Long aisId = AdherentInfoSaisonDao.getIdBySaisonAndAccount(item.getFk_saison_id(), item.getFk_account_id());
			if (aisId == null) { // Mode création
				logger.info("Création d'un item : " + item);
				String sql = "insert into adherent_info_saison (fk_account_id, fk_saison_id, fk_ffessm_licence_id, fk_subscription_id, "
						+ "fk_insurance_id, picture_authorisation, nead_certificate, fk_actual_training_id, fk_training_id, "
						+ "fk_team_id) values (:fk_account_id, :fk_saison_id, :fk_ffessm_licence_id, :fk_subscription_id, "
						+ ":fk_insurance_id, :picture_authorisation, :nead_certificate, :fk_actual_training_id, :fk_training_id, :fk_team_id)";

				try (Connection con = Sql2oDao.sql2o.open()) {
					Long insertedId = (Long) con.createQuery(sql, true).bind(item).executeUpdate().getKey();
					logger.info("ID généré : " + insertedId);
					return getItemById(insertedId);
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					return item;
				}
			} else {
				// L'objet était présent en base, on reprend son ID pour faire un update
				item.setId(aisId);
			}
		}

		// Mode modification
		logger.info("Mise à jour d'un item : " + item);
		String sql = "update adherent_info_saison set fk_account_id = :fk_account_id, fk_saison_id = :fk_saison_id, "
				+ "fk_ffessm_licence_id = :fk_ffessm_licence_id, fk_subscription_id = :fk_subscription_id, "
				+ "fk_insurance_id = :fk_insurance_id, picture_authorisation = :picture_authorisation, "
				+ "fk_actual_training_id = :fk_actual_training_id, fk_training_id = :fk_training_id, "
				+ "fk_team_id = :fk_team_id, fk_sick_note_id = :fk_sick_note_id, fk_parental_agreement_id = :fk_parental_agreement_id, "
				+ "validation_start = :validation_start, validation_general_informations = :validation_general_informations, "
				+ "validation_licence = :validation_licence, validation_sick_note = :validation_sick_note, "
				+ "validation_parental_agreement = :validation_parental_agreement, validation_payment_transmitted = :validation_payment_transmitted, "
				+ "validation_payment_cashed = :validation_payment_cashed, validation_comment = :validation_comment where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
		return getItemById(item.getId());
	}

	public static Boolean deleteItem(Long id) {
		logger.info("Suppression d'un item : {}", id);
		String sql = "delete from adherent_info_saison where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
