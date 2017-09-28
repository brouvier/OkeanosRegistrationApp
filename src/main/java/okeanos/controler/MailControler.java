package okeanos.controler;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okeanos.util.AppProperties;

public class MailControler {
	protected final static Logger logger = LoggerFactory.getLogger(MailControler.class);

	private static final String HOST_NAME = AppProperties.getProperties().mailHostName;
	private static final String HOST_PORT = AppProperties.getProperties().mailHostPort;
	private static final String HOST_LOGIN = AppProperties.getProperties().mailHostLogin;
	private static final String HOST_PASS = AppProperties.getProperties().mailHostPass;

	public static void sendMail(String to, String subject, String message) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(new Integer(HOST_PORT));
		email.setAuthenticator(new DefaultAuthenticator(HOST_LOGIN, HOST_PASS));
		// email.setSSLOnConnect(true);
		email.setFrom(HOST_LOGIN);
		email.addTo(to);
		email.setSubject("[" + AppProperties.APPLICATION_FULL_NAME + "] " + subject);
		email.setMsg(message);

		logger.debug("Send to : " + to);

		email.send();

		logger.debug("Mail send !");
	}

}
