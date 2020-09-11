package okeanos.restApp;

import static spark.Spark.before;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.ipAddress;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okeanos.restApp.resources.AccountResource;
import okeanos.restApp.resources.AdherentInfoResource;
import okeanos.restApp.resources.AdherentInfoSaisonResource;
import okeanos.restApp.resources.DashboardResource;
import okeanos.restApp.resources.DivingTrainingResource;
import okeanos.restApp.resources.FfessmLicenceResource;
import okeanos.restApp.resources.HockeyTeamResource;
import okeanos.restApp.resources.InsuranceResource;
import okeanos.restApp.resources.PropertiesResource;
import okeanos.restApp.resources.SaisonResource;
import okeanos.restApp.resources.SecurityResource;
import okeanos.restApp.resources.SickNoteResource;
import okeanos.restApp.resources.SubscriptionResource;
import okeanos.restApp.resources.SubscriptionTypeResource;
import okeanos.util.AppProperties;

public class OkeanosSparkApp {

	public static void main(String[] args) throws Exception {

		final Logger logger = LoggerFactory.getLogger(OkeanosSparkApp.class);

		ipAddress(AppProperties.getProperties().restHostName);
		port(new Integer(AppProperties.getProperties().restHostPort));

		String projectDir = System.getProperty("user.dir");
		String staticDir = "/src/main/ressources/public";
		externalStaticFileLocation(projectDir + staticDir);

		// On ne configure le certificat que s'il est renseigné dans les propriétés
		if (AppProperties.getProperties().keyStorePath != null) {
			secure(AppProperties.getProperties().keyStorePath, AppProperties.getProperties().keyStorePassword,
					AppProperties.getProperties().trustStorePath, AppProperties.getProperties().trustStorePassword);
		}

		// BrrSparkUtil.fullCORS();

		new AccountResource();
		new AdherentInfoResource();
		new AdherentInfoSaisonResource();
		new DashboardResource();
		new DivingTrainingResource();
		new FfessmLicenceResource();
		new HockeyTeamResource();
		new InsuranceResource();
		new SaisonResource();
		new SecurityResource();
		new SubscriptionResource();
		new SubscriptionTypeResource();
		new SickNoteResource();
		new PropertiesResource();

		before((request, response) -> {
			logger.info("Request [" + request.requestMethod() + "] receve from [" + request.headers("Origin")
					+ "], session [" + request.session().id() + "] for [" + request.pathInfo() + "] with body ["
					+ request.body() + "]");

			// Security Headers
			response.header("Referrer-Policy", "no-referrer");
			response.header("Feature-Policy", "");
			response.header("Content-Security-Policy", "default-src https");
			response.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
			// response.header("X-Content-Type-Options", "nosniff");

			// Appel du back par le front depuis une autre machine
			response.header("Access-Control-Allow-Credentials", "true");
			response.header("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
			response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
			response.header("Access-Control-Allow-Origin", AppProperties.getProperties().frontHostName);
			response.header("Access-Control-Expose-Headers", "curentAccountId, isLogin, isAdmin");
			response.header("Access-Control-Max-Age", "3600");

			request.session(false);

			logger.info("cookies :" + request.cookies().toString());

			// response.header("Set-Cookie",
			// "JSESSIONID=" + request.cookie("OkSession") +
			// ";SameSite=None;Secure;Max-Age=5");

			if (!request.cookies().containsKey("OkSession") && request.requestMethod() != "OPTIONS") {
				logger.info("Set OkSession cookie = " + request.session().id());
				response.header("Set-Cookie",
						"OkSession=" + request.session().id() + ";Path=/;SameSite=None;Secure;Max-Age=3600");
			}

		});

		// Le protocole CROS impose une requête préliminaire OPTIONS pour connaitre les
		// Origines authorisées
		options("/*", (request, response) -> {
			return true;
		});
	}
}
