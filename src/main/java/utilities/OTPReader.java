package utilities;

import config.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.search.FromStringTerm;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReader {

    private static final Pattern OTP_PATTERN = Pattern.compile("\\b\\d{6}\\b");

    public static String getLatestOTP() throws Exception {

        String email = ConfigReader.getInstance().getProperty("gmail.email");
        String password = ConfigReader.getInstance().getProperty("gmail.app.password");

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.ssl.enable", "true");

        Session session = Session.getInstance(props);

        Store store = session.getStore("imap");
        store.connect("imap.gmail.com", 993, email, password);

        Folder inbox = store.getFolder("INBOX");

        // Retry for 30 seconds
        for (int retry = 1; retry <= 15; retry++) {

            if (inbox.isOpen()) {
                inbox.close(false);
            }

            inbox.open(Folder.READ_ONLY);

            System.out.println("Checking Gmail... Attempt : " + retry);

            Message[] messages = inbox.getMessages();

            // Read latest mail first
            for (int i = messages.length - 1; i >= 0; i--) {

                Message message = messages[i];

                String from = message.getFrom()[0].toString();
                String subject = message.getSubject();

                // Ignore non-Scopely mails
                if (!(from.toLowerCase().contains("scopely")
                        || (subject != null && subject.toLowerCase().contains("scopely")))) {
                    continue;
                }

                System.out.println("--------------------------------");
                System.out.println("Message No : " + message.getMessageNumber());
                System.out.println("Subject    : " + subject);
                System.out.println("Time       : " + message.getReceivedDate());

                String content = getText(message);

                Matcher matcher = OTP_PATTERN.matcher(content);

                if (matcher.find()) {

                    String otp = matcher.group();

                    System.out.println("Latest OTP = " + otp);

                    inbox.close(false);
                    store.close();

                    return otp;
                }
            }

            Thread.sleep(2000);
        }

        if (inbox.isOpen()) {
            inbox.close(false);
        }

        store.close();

        throw new RuntimeException("OTP not received within 30 seconds.");
    }

    private static String getText(Part part) throws Exception {

        if (part.isMimeType("text/*")) {
            return part.getContent().toString();
        }

        if (part.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) part.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {

                String text = getText(multipart.getBodyPart(i));

                if (text != null && !text.isEmpty()) {
                    return text;
                }
            }
        }

        return "";
    }
}