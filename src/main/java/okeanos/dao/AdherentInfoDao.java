package okeanos.dao;

import java.util.Date;
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

	public static AdherentInfo newItem(Double fk_account_id, String firstname, String lastname, Date birsthday,
			String birthplace, String licence_number, String adresse, String zip_code, String city, String job,
			String tel_number, String mobile_number, String emergency_contact, String emergency_tel_number) {
		String sql = "insert into account (fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
				+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number) "
				+ "values (:fk_account_id, :firstname, :lastname, :birsthday, :birthplace, :licence_number, :adresse, :zip_code, "
				+ ":city, :job, :tel_number, :mobile_number, :emergency_contact, :emergency_tel_number)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("fk_account_id", fk_account_id)
					.addParameter("firstname", firstname).addParameter("lastname", lastname)
					.addParameter("birsthday", birsthday).addParameter("birthplace", birthplace)
					.addParameter("licence_number", licence_number).addParameter("adresse", adresse)
					.addParameter("zip_code", zip_code).addParameter("city", city).addParameter("job", job)
					.addParameter("tel_number", tel_number).addParameter("mobile_number", mobile_number)
					.addParameter("emergency_contact", emergency_contact)
					.addParameter("emergency_tel_number", emergency_tel_number).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static AdherentInfo getItemById(Long id) {
		String sql = "SELECT id, fk_account_id, firstname, lastname, birsthday, birthplace, licence_number, adresse, "
				+ "zip_code, city, job, tel_number, mobile_number, emergency_contact, emergency_tel_number, createdOn FROM adherent_info WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(AdherentInfo.class);
		}
	}

	public static AdherentInfo updateItem(AdherentInfo item) {
		String sql = "update adherent_info set fk_account_id = :fk_account_id, firstname = :firstname, lastname = :lastname, "
				+ "birsthday = :birsthday, birthplace = :birthplace, licence_number = :licence_number, adresse = :adresse, "
				+ "zip_code = :zip_code, city = :city, job = :job, tel_number = :tel_number, mobile_number = :mobile_number, "
				+ "emergency_contact = :emergency_contact, emergency_tel_number = :emergency_tel_number where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static void deleteItem(AdherentInfo item) {
		String sql = "delete from adherent_info where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}
	}

}
