package gui;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;

public class Email {
	private static String USERNAME;
	private static String PASSWORD;
	private static String RECIPIENT;

	private static String SUBJECT;
	private static String MESSAGE;
	
	public Email() {
		
	}
	
	public Email(String username, String password) {
		USERNAME = username;
		PASSWORD = password;
	}
	
	public static void sendFromGmail() {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", USERNAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME, PASSWORD);
					}
		});
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT));
			message.setSubject(SUBJECT);
			message.setText(MESSAGE);
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, RECIPIENT, PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
	
	
	public static String getSUBJECT() {
		return SUBJECT;
	}

	public static void setSUBJECT(String subject) {
		SUBJECT = subject;
	}

	public static String getMESSAGE() {
		return MESSAGE;
	}

	public static void setMESSAGE(String message) {
		MESSAGE = message;
	}
	
	
	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String username) {
		USERNAME = username;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String password) {
		PASSWORD = password;
	}

	public static String getRECIPIENT() {
		return RECIPIENT;
	}

	public static void setRECIPIENT(String recipient) {
		RECIPIENT = recipient;
	}
}
