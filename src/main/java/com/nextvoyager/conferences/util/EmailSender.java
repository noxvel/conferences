package com.nextvoyager.conferences.util;

import com.nextvoyager.conferences.model.dao.exeption.DAOConfigurationException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for send emails in another thread
 */
public class EmailSender {
    public static final String PROPERTIES_FILE = "email.properties";
    public static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new EmailConfigException("Properties file '" + PROPERTIES_FILE + "' is missing!");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException ex) {
            throw new EmailConfigException("Cannot load properties file '" + PROPERTIES_FILE + "' .", ex);
        }
    }

    private static String getProperty(String key) throws DAOConfigurationException {
        String property = PROPERTIES.getProperty(key);

        if (property == null || property.trim().length() == 0) {
                throw new EmailConfigException("Require property '" + key + "' is missing in  properties file '" +
                        PROPERTIES_FILE + "'!");
        }
        return property;
    }

    public static void send(List<String> sendToEmails, String subject, String body) {
        new SendEmail(sendToEmails, subject, body).start();
    }

    private static class SendEmail extends Thread{
        private final List<String> toEmails;
        private final String subject;
        private final String body;

        public SendEmail(List<String> toEmails, String subject, String body) {
            this.toEmails = toEmails;
            this.subject = subject;
            this.body = body;
        }

        @Override
        public void run() {

            final String login = getProperty("login");
            final String fromEmail = getProperty("email");
            final String password = getProperty("password");

            Properties props = new Properties();
            props.put("mail.smtp.host", getProperty("host")); //SMTP Host
            props.put("mail.smtp.port", getProperty("port")); //TLS Port
            Boolean enableAuth = Boolean.parseBoolean(getProperty("enable-auth"));
            props.put("mail.smtp.auth", enableAuth); //enable authentication
            props.put("mail.smtp.starttls.enable", enableAuth); //enable STARTTLS
//        props.put("mail.smtp.ssl.enable", enableAuth); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = null;
            if (enableAuth) {
                auth = new Authenticator() {
                    //override the getPasswordAuthentication method
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                };
            }
            Session session = Session.getInstance(props, auth);
            // Used to debug SMTP issues
            session.setDebug(true);

            System.out.println(("Text to users - " + toEmails + ", text - " + body));
            sendEmail(session,fromEmail,toEmails,subject,body);
        }

        /**
         * Utility method to send email
         */
        private static void sendEmail(Session session, String fromEmail, List<String> toEmails, String subject, String body){
            try {
                MimeMessage msg = new MimeMessage(session);
                //set message headers
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8bit");

                msg.setFrom(new InternetAddress(fromEmail));
                msg.setRecipients(Message.RecipientType.TO, String.join(",", toEmails));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                msg.setSubject(subject, "UTF-8");

                msg.setText(body, "UTF-8");

                Transport.send(msg);
            }
            catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
