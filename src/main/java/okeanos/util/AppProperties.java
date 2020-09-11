package okeanos.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppProperties {
	private static Logger logger = LoggerFactory.getLogger(AppProperties.class);

	// App
	public static final String APPLICATION_NAME = "okeanos_rest";
	public static final String APPLICATION_VERSION = "3.0.0";
	public static final String APPLICATION_FULL_NAME = "Okeanos Registration App";

	protected static final String PROPERTIES_FILE_NAME = "okeanos_rest.properties";

	protected static final String LOGGER_NAME = APPLICATION_NAME + "_logger";
	protected static final String LOG_FILE_NAME = APPLICATION_NAME + ".log";

	public static final String STATIC_FILE_PATH = "/public";
	public static final String API_CONTEXT = "/api/v1/";

	public final static String DB_CONNECT_STRING = "jdbc:h2:file:./h2/okeanos;";
	public final static String DB_CONNECT_OPTION = "INIT=create schema if not exists okeanos\\;use okeanos;TRACE_LEVEL_FILE=0;";

	public final static String SYSTEM_USER_DIT = System.getProperty("user.dir");

	public final static String DB_LOCATION = "filesystem:" + SYSTEM_USER_DIT + "/h2";

	public final static String TEMPLATE_FILE = SYSTEM_USER_DIT + "/template/certificate_template.html";
	public final static String TEMPLATE_LOGO = "./template/OkeanosLogo.png";
	public final static String TEMPLATE_SIGN = "./template/OkeanosSignature.png";

	public final static String MAIL_TEMPLATE_FILE = SYSTEM_USER_DIT + "/template/mail_template.html";

	// Formater
	public static final DateFormat DATE_FORMATEUR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATE_FORMATEUR_MS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final DateFormat DATE_FORMATEUR_DAY = new SimpleDateFormat("dd/MM/yyyy");

	protected static AppProperties appProperties;

	public final String logLevel;

	public final String restHostName;
	public final String restHostPort;
	public final String frontRestAcces;
	public final String frontHostName;
	public final String frontModeDebug;
	public final String dbLogin;
	public final String dbPassword;

	public final String keyStorePath;
	public final String keyStorePassword;
	public final String trustStorePath;
	public final String trustStorePassword;

	public final String mailHostName;
	public final String mailHostPort;
	public final String mailHostLogin;
	public final String mailHostPass;

	public static AppProperties getProperties() {
		if (appProperties == null) {
			appProperties = new AppProperties();
		}
		return appProperties;
	}

	/**
	 * Create properties with properties file BoMgt.properties
	 */
	protected AppProperties() {

		final Properties prop = loadProp();

		logLevel = prop.getProperty("logLevel");

		restHostName = prop.getProperty("restHostName");
		restHostPort = prop.getProperty("restHostPort");
		frontRestAcces = prop.getProperty("frontRestAcces");
		frontHostName = prop.getProperty("frontHostName");
		frontModeDebug = prop.getProperty("frontModeDebug");

		dbLogin = prop.getProperty("dbLogin");
		dbPassword = prop.getProperty("dbPassword");

		keyStorePath = prop.getProperty("keyStorePath");
		keyStorePassword = prop.getProperty("keyStorePassword");
		trustStorePath = prop.getProperty("trustStorePath");
		trustStorePassword = prop.getProperty("trustStorePassword");

		mailHostName = prop.getProperty("mailHostName");
		mailHostPort = prop.getProperty("mailHostPort");
		mailHostLogin = prop.getProperty("mailHostLogin");
		mailHostPass = prop.getProperty("mailHostPass");

	}

	/**
	 * Read properties file
	 *
	 * @return
	 */
	protected Properties loadProp() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(PROPERTIES_FILE_NAME);

			// load properties to project root folder
			prop.load(input);

		} catch (final FileNotFoundException io) {
			logger.warn("Fichier de properties manquant ...");
			prop = generateEmptyPropertieFile();
		} catch (final IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}

		}

		return prop;
	}

	/**
	 * Generate empty propertie file
	 */
	protected Properties generateEmptyPropertieFile() {
		final Properties prop = new Properties();

		// set the default properties value
		prop.setProperty("logLevel", "WARNING");

		prop.setProperty("restHostName", "localhost");
		prop.setProperty("restHostPort", "8080");
		prop.setProperty("frontRestAcces", "https://localhost");
		prop.setProperty("frontHostName", "https://localhost");
		prop.setProperty("frontModeDebug", "true");

		prop.setProperty("dbLogin", "**Database Login**");
		prop.setProperty("dbPassword", "**Database Password**");

		prop.setProperty("keyStorePath", "./security/**KeyStore file**");
		prop.setProperty("keyStorePassword", "**KeyStore Password**");

		prop.setProperty("mailHostName", "ns0.ovh.net");
		prop.setProperty("mailHostPort", "587");
		prop.setProperty("mailHostLogin", "webmaster@okeanos-grenoble.fr");
		prop.setProperty("mailHostPass", "**To replace**");

		OutputStream output = null;

		try {
			output = new FileOutputStream(PROPERTIES_FILE_NAME);

			// save properties to project root folder
			prop.store(output, null);

		} catch (final IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

}
