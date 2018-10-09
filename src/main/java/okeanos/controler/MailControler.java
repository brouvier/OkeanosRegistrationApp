package okeanos.controler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
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

	public static void sendHtmlMail(String to, String subject, String message) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(HOST_NAME);
		email.setSmtpPort(new Integer(HOST_PORT));
		email.setAuthenticator(new DefaultAuthenticator(HOST_LOGIN, HOST_PASS));
		// email.setSSLOnConnect(true);
		email.setFrom(HOST_LOGIN);
		email.addTo(to);
		email.setSubject("[" + AppProperties.APPLICATION_FULL_NAME + "] " + subject);

		// set the html message
		email.setHtmlMsg(getTemplateContent());

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		logger.debug("Send to : " + to);

		email.send();

		logger.debug("Mail send !");
	}

	protected static String getTemplateContent() {
		String template = "";

		logger.debug("TEMPLATE_FILE [" + AppProperties.MAIL_TEMPLATE_FILE + "]");

		try {
			InputStream in = new FileInputStream(AppProperties.MAIL_TEMPLATE_FILE);
			template = IOUtils.toString(in, Charset.forName("UTF-8"));
			IOUtils.closeQuietly(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return template;
	}

}
