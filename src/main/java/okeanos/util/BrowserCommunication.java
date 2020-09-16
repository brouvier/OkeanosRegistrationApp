package okeanos.util;

import static spark.Spark.before;
import static spark.Spark.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okeanos.restApp.OkeanosSparkApp;
import spark.Request;
import spark.Response;

public class BrowserCommunication {

	final static Logger logger = LoggerFactory.getLogger(OkeanosSparkApp.class);
	public final static String SESSION_TOKEN_NAME = "OkoeanosSessionToken";
	public final static Integer SESSION_MAX_AGE = 21600; // 6 heures

	public BrowserCommunication() {
		setHeaderSecurity();
		InitUserSession();
		enableCORS();
	}

	/**
	 * Surcharge les valeurs par défaut des entêtes HTTP pour limiter les risques
	 */
	public void setHeaderSecurity() {

		before((request, response) -> {
			logger.debug("Request [" + request.requestMethod() + "] receve from [" + request.headers("Origin")
					+ "], session [" + request.session().id() + "] for [" + request.pathInfo() + "]");

			// Security Headers
			response.header("Referrer-Policy", "no-referrer");
			response.header("Feature-Policy", "");
			response.header("Content-Security-Policy", "default-src https");
			response.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
			// response.header("X-Content-Type-Options", "nosniff");
		});
	}

	/**
	 * Génère un cookie contenant le ticket de session de l'utilisateur
	 */
	public void InitUserSession() {

		before((request, response) -> {
			logger.debug("Cookies : " + request.cookies().toString());
			if (!request.cookies().containsKey(SESSION_TOKEN_NAME) && request.requestMethod() != "OPTIONS") {
				logger.info("Set cookie for session token = " + request.session().id());
				response.header("Set-Cookie", SESSION_TOKEN_NAME + "=" + request.session().id()
						+ ";Path=/;SameSite=None;Secure;Max-Age=" + SESSION_MAX_AGE);
			}
		});
	}

	/**
	 * Retire du cookie le ticket de session de l'utilisateur
	 */
	public static void destroyUserSession(Response response, Request request) {
		response.header("Set-Cookie", SESSION_TOKEN_NAME
				+ "=\"\";Version=1;Expires=Thu, 01-Jan-1970 00:00:00 GMT;Max-Age=0;SameSite=None;Secure;");
		request.session().invalidate();
	}

	/**
	 * Autorise un accès CORS si l'utilisateur passe par une adresse préalablement
	 * autorisée
	 */
	public static void enableCORS() {

		before((request, response) -> {
			if (request.headers("Origin") != null
					&& AppProperties.getProperties().frontHostName.equals(request.headers("Origin"))) {
				// Appel du back par le front depuis une autre machine
				response.header("Access-Control-Allow-Credentials", "true");
				response.header("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
				response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
				response.header("Access-Control-Allow-Origin", AppProperties.getProperties().frontHostName);
				response.header("Access-Control-Expose-Headers", "curentAccountId, isLogin, isAdmin");
				response.header("Access-Control-Max-Age", SESSION_MAX_AGE.toString());
			} else {
				logger.debug("CORS desable for origin [" + request.headers("Origin") + "]");
			}
		});

		// Le protocole CROS impose une requête préliminaire OPTIONS pour connaitre les
		// Origines authorisées
		options("/*", (request, response) -> {
			return true;
		});
	}

}
