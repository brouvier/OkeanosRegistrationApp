package okeanos.controler;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import okeanos.dao.AccountDao;
import okeanos.model.Account;

public class LoginControler {

	public static Account loginAcount(String mail, String password) throws IllegalArgumentException {

		if (mail == null || "".equals(mail)) {
			throw new IllegalArgumentException("email is empty");
		}
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("password is empty");
		}

		Account account = AccountDao.getItemByMail(mail);
		if (account == null) {
			throw new IllegalArgumentException("account doesn't exist");
		}
		String hashedPass = hash(account.getSalt() + password);

		System.out.println("mail : " + mail + "\tpassword : " + password);
		System.out.println("hashedPass : " + hashedPass);
		System.out.println("savedPass  : " + account.getPassword());

		if (!account.getPassword().equals(hashedPass)) {
			throw new IllegalArgumentException("wrong password");
		}

		return account;
	}

	public static Account createAccount(String mail, String password) throws IllegalArgumentException {
		if (mail == null || "".equals(mail)) {
			throw new IllegalArgumentException("email is empty");
		}
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("password is empty");
		}
		if (AccountDao.getItemByMail(mail) != null) {
			throw new IllegalArgumentException("account already exist");
		}

		// Genertion d'une chaine unique pour le sel
		String saltString = new BigInteger(130, new SecureRandom()).toString(32);
		String salt = hash(saltString);

		// Sauvegarde du pass salé
		String hashedPass = hash(salt + password);

		return AccountDao.newItem(mail, salt, hashedPass, true);
	}

	protected static String hash(String message) {
		try {
			byte[] b = message.getBytes(StandardCharsets.UTF_8);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] h = md.digest(b);
			String hash = new String(h);
			System.out.println("Hash - message : " + message);
			System.out.println("Hash - message byte : " + b);
			System.out.println("Hash - hashed message byte : " + h);
			System.out.println("Hash - hashed message : " + hash);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
