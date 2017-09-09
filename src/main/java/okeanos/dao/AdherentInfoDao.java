package okeanos.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;

import okeanos.model.AdherentInfo;

public class AdherentInfoDao {

	private static Logger logger = LoggerFactory.getLogger(AdherentInfoDao.class);

	public static List<AdherentInfo> getAllItems() {
		String sql = "SELECT id, fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
				+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number, createdOn FROM adherent_info";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(AdherentInfo.class);
		}
	}

	public static AdherentInfo getItemById(Long id) {
		String sql = "SELECT id, fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
				+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number, createdOn FROM adherent_info WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(AdherentInfo.class);
		}
	}

	public static AdherentInfo getItemByAccountId(Long id) {
		String sql = "SELECT id, fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
				+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number, createdOn FROM adherent_info WHERE fk_account_id = :id";

		AdherentInfo ai = null;
		try (Connection con = Sql2oDao.sql2o.open()) {
			ai = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(AdherentInfo.class);
		}
		if (ai == null) {
			ai = new AdherentInfo(null, id, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null);
		}
		return ai;
	}

	public static AdherentInfo save(AdherentInfo item) {

		if (item == null) {
			logger.error("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode création
			logger.debug("Création d'un item : " + item);
			String sql = "insert into adherent_info (fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
					+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number) "
					+ "values (:fk_account_id, :firstname, :lastname, :birsthday, :birthplace, :licence_number, :adresse, :zip_code, "
					+ ":city, :job, :tel_number, :mobile_number, :emergency_contact, :emergency_tel_number)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true).bind(item).executeUpdate().getKey();
				logger.debug("ID généré : {}", insertedId);
				return getItemById(insertedId);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return item;
			}

		} else { // Mode modification
			logger.debug("Mise à jour d'un item : " + item);
			String sql = "update adherent_info set fk_account_id = :fk_account_id, firstname = :firstname, lastname = :lastname, "
					+ "birsthday = :birsthday, birthplace = :birthplace, licence_number = :licence_number, adresse = :adresse, "
					+ "zip_code = :zip_code, city = :city, job = :job, tel_number = :tel_number, mobile_number = :mobile_number, "
					+ "emergency_contact = :emergency_contact, emergency_tel_number = :emergency_tel_number where id = :id";

			try (Connection con = Sql2oDao.sql2o.open()) {
				con.createQuery(sql).bind(item).executeUpdate();
			}
			return getItemById(item.getId());

		}
	}

	public static Boolean deleteItem(Long id) {
		logger.debug("Suppression d'un item : {}", id);
		String sql = "delete from adherent_info where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
