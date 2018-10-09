package okeanos.restApp;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.secure;

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
	}
}
