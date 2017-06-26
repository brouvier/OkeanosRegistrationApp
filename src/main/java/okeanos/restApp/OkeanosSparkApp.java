package okeanos.restApp;

import static spark.SparkBase.externalStaticFileLocation;
import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import okeanos.restApp.resources.AccountResource;
import okeanos.restApp.resources.AdherentInfoResource;
import okeanos.restApp.resources.AdherentInfoSaisonResource;
import okeanos.restApp.resources.DivingTrainingResource;
import okeanos.restApp.resources.FfessmLicenceResource;
import okeanos.restApp.resources.HockeyTeamResource;
import okeanos.restApp.resources.InsuranceResource;
import okeanos.restApp.resources.SaisonResource;
import okeanos.restApp.resources.SecurityResource;
import okeanos.restApp.resources.SubscriptionResource;
import okeanos.restApp.resources.SubscriptionTypeResource;
import okeanos.util.AppProperties;
import spark.Spark;

public class OkeanosSparkApp {

	public static void main(String[] args) throws Exception {

		setIpAddress(AppProperties.getProperties().restHostName);
		setPort(new Integer(AppProperties.getProperties().restHostPort));

		if ("localhost".equals(AppProperties.getProperties().restHostName)) {
			String projectDir = System.getProperty("user.dir");
			String staticDir = "/src/main/ressources/public";
			externalStaticFileLocation(projectDir + staticDir);
		} else {
			staticFileLocation(AppProperties.STATIC_FILE_PATH);
		}

		Spark.setSecure(AppProperties.keyStorePath, AppProperties.keyStorePassword, AppProperties.trustStorePath,
				AppProperties.trustStorePassword);

		new AccountResource();
		new AdherentInfoResource();
		new AdherentInfoSaisonResource();
		new DivingTrainingResource();
		new FfessmLicenceResource();
		new HockeyTeamResource();
		new InsuranceResource();
		new SaisonResource();
		new SecurityResource();
		new SubscriptionResource();
		new SubscriptionTypeResource();
	}
}
