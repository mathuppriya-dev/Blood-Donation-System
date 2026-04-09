import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class TestEmail {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("union20032003@gmail.com", "iryr xqfg nirx cxjw");
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("union20032003@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("blooddonationsystem1@gmail.com"));
            message.setSubject("Test Email");
            message.setText("This is a test email from Blood Donation System");
            
            Transport.send(message);
            System.out.println("Test email sent successfully!");
            
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}