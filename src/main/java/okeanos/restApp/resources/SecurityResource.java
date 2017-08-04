package okeanos.restApp.resources;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;

import okeanos.controler.LoginControler;
import okeanos.dao.AccountDao;
import okeanos.model.Account;
import okeanos.util.AppProperties;
import spark.Request;

public class SecurityResource extends AbstractResource {

	protected static HashMap<String, Long> sessionList = new HashMap<String, Long>();

	protected String ressourcePath = AppProperties.API_CONTEXT + "/security";

	public static Boolean isLogin(Request request) {
		return sessionList.containsKey(request.session().id());
	}

	public static Long currentAccountId(Request request) {
		return sessionList.get(request.session().id());
	}

	public static Boolean isAdmin(Request request) {
		if (!isLogin(request))
			return false;

		Account user = AccountDao.getItemById(sessionList.get(request.session().id()));

		if (user == null)
			return false;

		return user.isAdmin();
	}

	public static Long getCurrentUserId(Request request) {
		return sessionList.get(request.session().id());
	}

	public static Boolean isLoginAndCurrentAccount(Request request, Long accountId) {
		return isLogin(request) & getCurrentUserId(request).equals(accountId);
	}

	public SecurityResource() {

		/**
		 * URL d'inscription
		 */
		post(ressourcePath + "/signup", (request, response) -> {

			AccountBean bean = gson.fromJson(request.body(), AccountBean.class);
			System.out.println("signup request receve : " + bean.mail);

			try {
				Account user = LoginControler.createAccount(bean.mail, bean.password);
				sessionList.put(request.session().id(), user.getId());
				setSecurity(request, response);
			} catch (Exception e) {
				return e.getMessage();
			}

			return true;
		});

		/**
		 * URL de connexion
		 */
		post(ressourcePath + "/login", (request, response) -> {

			AccountBean bean = gson.fromJson(request.body(), AccountBean.class);
			System.out.println("login request receve : " + bean.mail);
			try {
				Account user = LoginControler.loginAcount(bean.mail, bean.password);
				sessionList.put(request.session().id(), user.getId());
				setSecurity(request, response);
			} catch (Exception e) {
				return e.getMessage();
			}

			return true;
		});

		/**
		 * URL deconnexion
		 */
		get(ressourcePath + "/logout", (request, response) -> {
			sessionList.remove(request.session().id());
			request.session().invalidate();

			return true;
		});
	}

	private class AccountBean {
		public String mail;
		public String password;
	}

}
