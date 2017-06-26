package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.AdherentInfo;

public class AdherentInfoDao {

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

	public static AdherentInfo save(AdherentInfo item) {

		if (item == null || "".equals(item.getFk_account_id())) {
			System.out.println("Error : cannot save empty item !");
			return null;
		}

		if (item.getId() == null) { // Mode crÃ©ation
			System.out.println("CrÃ©ation d'un item");
			String sql = "insert into account (fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
					+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number) "
					+ "values (:fk_account_id, :firstname, :lastname, :birsthday, :birthplace, :licence_number, :adresse, :zip_code, "
					+ ":city, :job, :tel_number, :mobile_number, :emergency_contact, :emergency_tel_number)";

			try (Connection con = Sql2oDao.sql2o.open()) {
				Long insertedId = (Long) con.createQuery(sql, true)
						.addParameter("fk_account_id", item.getFk_account_id())
						.addParameter("firstname", item.getFirstname()).addParameter("lastname", item.getLastname())
						.addParameter("birsthday", item.getBirsthday()).addParameter("birthplace", item.getBirthplace())
						.addParameter("licence_number", item.getLicence_number())
						.addParameter("adresse", item.getAdresse()).addParameter("zip_code", item.getZip_code())
						.addParameter("city", item.getCity()).addParameter("job", item.getJob())
						.addParameter("tel_number", item.getTel_number())
						.addParameter("mobile_number", item.getMobile_number())
						.addParameter("emergency_contact", item.getEmergency_contact())
						.addParameter("emergency_tel_number", item.getEmergency_tel_number()).executeUpdate().getKey();
				System.out.println("ID généré : " + insertedId);
				return getItemById(insertedId);
			}

		} else { // Mode modification
			System.out.println("Mise Ã  jour d'un item");
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
		System.out.println("Suppression d'un item : " + id);
		String sql = "delete from adherent_info where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
