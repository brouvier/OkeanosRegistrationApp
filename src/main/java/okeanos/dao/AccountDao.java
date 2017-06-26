package okeanos.dao;

import java.util.List;

import org.sql2o.Connection;

import okeanos.model.Account;

public class AccountDao {

	public static List<Account> getAllItems() {
		String sql = "SELECT id, mail, password, admin, createdOn FROM account";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Account.class);
		}
	}

	public static Account getItemById(Long id) {
		String sql = "SELECT id, mail, password, admin, createdOn FROM account WHERE id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Account.class);
		}
	}

	public static Account getItemByMail(String mail) {
		String sql = "SELECT id, mail, password, admin, createdOn FROM account WHERE mail = :mail";

		try (Connection con = Sql2oDao.sql2o.open()) {
			return con.createQuery(sql).addParameter("mail", mail).executeAndFetchFirst(Account.class);
		}
	}

	public static Account newItem(String mail, String password, Boolean admin) {
		String sql = "insert into account (mail, password, admin) values (:mail, :password, :admin)";

		try (Connection con = Sql2oDao.sql2o.open()) {
			Long insertedId = (Long) con.createQuery(sql, true).addParameter("mail", mail)
					.addParameter("password", password).addParameter("admin", admin).executeUpdate().getKey();
			return getItemById(insertedId);
		}
	}

	public static Account updateItem(Account item) {
		String sql = "update account set mail = :mail, admin = :admin where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static Account updatePassword(Account item) {
		String sql = "update account set password = :password where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).bind(item).executeUpdate();
		}

		return item;
	}

	public static Boolean deleteItem(Long id) {
		String sql = "delete from account where id = :id";

		try (Connection con = Sql2oDao.sql2o.open()) {
			con.createQuery(sql).addParameter("id", id).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
