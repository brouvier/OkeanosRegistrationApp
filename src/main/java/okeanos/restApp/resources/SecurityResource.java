package okeanos.restApp.resources;

import static spark.Spark.get;

import java.util.HashMap;

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

	public SecurityResource() {

		/**
		 * URL de connexion
		 */
		get(ressourcePath + "/login/:mail/:pass", (request, response) -> {

			String userMail = request.params(":mail");
			String userPass = request.params(":pass");

			if (userMail == null || "".equals(userMail)) {
				return false;
			}

			if (userPass == null || "".equals(userPass)) {
				return false;
			}

			Account user = AccountDao.getItemByMail(userMail);
			if (user == null) {
				return false;
			}

			if (!user.getPassword().equals(userPass)) {
				return false;
			}

			sessionList.put(request.session().id(), user.getId());
			setSecurity(request, response);

			return true;
		});

		/**
		 * URL de d�connection
		 */
		get(ressourcePath + "/logout", (request, response) -> {
			sessionList.remove(request.session().id());
			request.session().invalidate();

			return true;
		});

		/**
		 * URL d'inscription
		 */
		get(ressourcePath + "/signup/:mail/:pass", (request, response) -> {

			String userMail = request.params(":mail");
			String userPass = request.params(":pass");

			System.out.println("signup request receve : " + userMail);

			if (userMail == null || "".equals(userMail)) {
				return false;
			}

			if (userPass == null || "".equals(userPass)) {
				return false;
			}

			Account user = AccountDao.newItem(userMail, userPass, true);
			sessionList.put(request.session().id(), user.getId());
			setSecurity(request, response);

			return true;
		});
	}

}
