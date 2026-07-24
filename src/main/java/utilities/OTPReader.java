package utilities;

import config.ConfigReader;
import jakarta.mail.*;
import jakarta.mail.search.FromStringTerm;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPReader {

    public static String getLatestOTP() throws Exception {

        String email = ConfigReader.getInstance().getProperty("gmail.email");
        String password = ConfigReader.getInstance().getProperty("gmail.app.password");

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.ssl.enable", "true");

        Session session = Session.getInstance(props);

        Store store = session.getStore("imap");

        store.connect(
                "imap.gmail.com",
                993,
                email,
                password
        );

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Search emails from Scopely
        Message[] messages = inbox.search(new FromStringTerm("scopely"));

        if (messages.length == 0) {
            messages = inbox.getMessages();
        }

        // Read latest email first
        for (int i = messages.length - 1; i >= 0; i--) {

            Message message = messages[i];

            String content = getText(message);

            Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {

                inbox.close(false);
                store.close();

                return matcher.group();
            }
        }

        inbox.close(false);
        store.close();

        throw new RuntimeException("OTP not found.");

    }

    private static String getText(Part p) throws Exception {

        if (p.isMimeType("text/*")) {
            return p.getContent().toString();
        }

        if (p.isMimeType("multipart/*")) {

            Multipart mp = (Multipart) p.getContent();

            for (int i = 0; i < mp.getCount(); i++) {

                String text = getText(mp.getBodyPart(i));

                if (!text.isEmpty()) {
                    return text;
                }
            }
        }

        return "";
    }
}