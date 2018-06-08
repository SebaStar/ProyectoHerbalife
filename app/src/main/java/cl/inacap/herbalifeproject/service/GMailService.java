package cl.inacap.herbalifeproject.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Conexión con el API de GMail.
 * @author Sebastián
 */

public class GMailService {

    private static final String GMAIL_SMTP_PORT_TLS = "587";
    private static final String SMTP_AUTH = "true";
    private static final String START_TLS = "true";
    private static final String EMAIL_HOST = "smtp.gmail.com";
    private static final int TIMEOUT = 5000;

    private String fromEmail;
    private String fromPassword;
    private List toEmailList;
    private String emailSubject;
    private String emailBody;

    private Properties emailProperties;
    private Session mailSession;
    private MimeMessage emailMessage;

    /**
     * Costructor de la clase.
     * @param fromEmail Email de soporte.
     * @param fromPassword Contraseña email de soporte.
     * @param toEmailList  Listado de emails a la cual se enviarán.
     * @param emailSubject Asusnto del email de soporte.
     * @param emailBody Cuerpo del email de soporte.
     */
    public GMailService(String fromEmail, String fromPassword, List toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", GMAIL_SMTP_PORT_TLS);
        emailProperties.put("mail.smtp.auth", SMTP_AUTH);
        emailProperties.put("mail.smtp.starttls.enable", START_TLS);
        emailProperties.put("mail.smtp.connectiontimeout", TIMEOUT);
        emailProperties.put("mail.smtp.timeout", TIMEOUT);
    }

    /**
     *
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void createEmailMessage() throws MessagingException, UnsupportedEncodingException {
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        for (Object toEmail : toEmailList) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(toEmail)));
        }
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");
    }

    /**
     *
     * @throws MessagingException
     */
    public void sendEmail() throws MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(EMAIL_HOST, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }
}
