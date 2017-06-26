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

public class AppProperties {

	// App
	public static final String APPLICATION_NAME = "okeanos_rest";
	public static final String APPLICATION_VERSION = "2.0.0";

	protected static final String PROPERTIES_FILE_NAME = "okeanos_rest.properties";

	protected static final String LOGGER_NAME = APPLICATION_NAME + "_logger";
	protected static final String LOG_FILE_NAME = APPLICATION_NAME + ".log";

	public static final String STATIC_FILE_PATH = "/public";
	public static final String API_CONTEXT = "/api/v1";

	public final static String keyStorePath = "./security/okeanosKeyStore.jks";
	public final static String keyStorePassword = "Ok34n0sStorePass";
	public final static String trustStorePath = null;
	public final static String trustStorePassword = null;

	// Formater
	public static final DateFormat DATE_FORMATEUR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATE_FORMATEUR_MS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	protected static AppProperties appProperties;

	public final String logLevel;

	public final String restHostName;
	public final String restHostPort;
	public final String frontHostName;

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
		frontHostName = prop.getProperty("frontHostName");

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
			System.out.println("Fichier de properties manquant ...");
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
		prop.setProperty("frontHostName", "localhost");

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
