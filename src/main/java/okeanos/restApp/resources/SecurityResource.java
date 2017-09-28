package okeanos.restApp.resources;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;

import org.apache.commons.collections4.bidimap.TreeBidiMap;

import okeanos.controler.LoginControler;
import okeanos.controler.MailControler;
import okeanos.dao.AccountDao;
import okeanos.model.Account;
import okeanos.util.AppProperties;
import spark.Request;
import spark.Response;

public class SecurityResource extends AbstractResource {

	/* Liste des sessions en cours : ticket de session : Id du compte */
	protected static TreeBidiMap<String, Long> sessionList = new TreeBidiMap<String, Long>();

	/*
	 * Liste des demandes de changement de mot de passe : Id du compte : Ticket de
	 * renouvellement
	 */
	protected static HashMap<Long, String> passwordUpdateList = new HashMap<Long, String>();

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

		Account user = AccountDao.getItemById(sessionList.get(request.session().id()), false);

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
				registerSession(request, response, user);
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
				registerSession(request, response, user);

				// On supprime les demandes de changement de mot de passe en cours
				if (passwordUpdateList.containsKey(user.getId()))
					passwordUpdateList.remove(user.getId());
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

		/**
		 * URL de demande de renouvellement de mot de passe
		 */
		get(ressourcePath + "/requestNewPass/:mail", (request, response) -> {
			setSecurity(request, response);

			// On contrôle que le mail correspond bien à un compte
			Account user = AccountDao.getItemByMail(request.params(":mail"));
			logger.info("Demande de changement de mot de passe pour : " + user);

			if (user == null || "".equals(user.getMail())) {
				return gson.toJson(new MessageAlertBean("alert-danger", "Adresse mail inconnue"));
			}

			// Crétion d'un ticket, si un ticket existait déjà il sera écrasé
			String ticket = LoginControler.getRandomString();
			passwordUpdateList.put(user.getId(), ticket);

			logger.info("Génération d'un ticket : " + ticket);

			// Envois d'un mail
			StringBuilder message = new StringBuilder();
			message.append("Bonjour,\n\nUne demande de modification de votre mot de passe a été reçu.\n")
					.append("Si vous êtes l'auteur de cette demande, suivez le lien suivant :\n")
					.append(AppProperties.getProperties().frontRestAcces).append(":")
					.append(AppProperties.getProperties().restHostPort).append("/#/passwordUpdate/" + ticket + "\n\n")
					.append("Bonne journée\nCordialement,\nL'équipe OKEANOS");
			try {
				MailControler.sendMail(user.getMail(), "Changement de mot de passe", message.toString());
			} catch (Exception e) {
				return gson.toJson(new MessageAlertBean("alert-danger",
						"Une erreur est survenue lors de l'envois du mail : " + e.getMessage()));
			}
			logger.info("Demande envoyé par mail : " + user.getMail());

			return gson.toJson(
					new MessageAlertBean("alert-info", "Demande de renouvellement de mot de passe envoyé par mail"));
		});

		/**
		 * URL de renouvellement de mot de passe
		 */
		post(ressourcePath + "/updatePass/:ticket", (request, response) -> {
			setSecurity(request, response);

			// Récupération des paramêtres
			String ticket = request.params(":ticket");
			AccountBean bean = gson.fromJson(request.body(), AccountBean.class);
			logger.info("Update password request receve for : " + bean.mail);

			// On contrôle que le ticket existe
			if (!passwordUpdateList.containsValue(ticket)) {
				return gson.toJson(new MessageAlertBean("alert-danger",
						"Ticket de changement de mot de passe non référencé. Demande rejetée !"));
			}

			// On contrôle que le ticket est bien pour cet utilisateur
			Account user = AccountDao.getItemByMail(bean.mail);
			logger.info(user.toString());
			if (user == null || user.getId() == null) {
				return gson.toJson(new MessageAlertBean("alert-danger", "Utilisateur inconnu !"));
			}
			if (!passwordUpdateList.containsKey(user.getId())) {
				return gson.toJson(new MessageAlertBean("alert-danger",
						"Aucune demande de changement de mot de passe connue pour cet utilisateur !"));
			}

			if (!passwordUpdateList.get(user.getId()).equals(ticket)) {
				return gson.toJson(new MessageAlertBean("alert-danger",
						"Erreur de correspondance entre la demande de changement de mot de passe et l'utilisateur !"));
			}

			// Mise à jour du mot de passe
			LoginControler.updatePassword(user, bean.password);

			// Supression du ticket
			passwordUpdateList.remove(user.getId());

			// Mise à jour de la session
			registerSession(request, response, user);

			return gson.toJson(new MessageAlertBean("alert-info", "Mot de passe mis à jour avec succès"));
		});
	}

	/**
	 * Referencement de la session pour un utilisateur
	 */
	protected void registerSession(Request request, Response response, Account user) {
		// Suppression d'autres sessions de cet utilisateur
		if (sessionList.containsValue(user.getId())) {
			sessionList.removeValue(user.getId());
		}

		// Référencement de la nouvelle session
		sessionList.put(request.session().id(), user.getId());

		// Ajout des infos d'authentification dans l'en-tête de la réponse
		setSecurity(request, response);
	}

	/**
	 * Structure de données pour les échanges avec le front
	 */
	private class AccountBean {
		public String mail;
		public String password;
	}

	private class MessageAlertBean {
		public String level;
		public String message;

		public MessageAlertBean(String level, String message) {
			this.level = level;
			this.message = message;
		}
	}

}
