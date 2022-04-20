package by.ganevich.mail;

import by.ganevich.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    private static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public String sendEmail(User user) throws MessagingException {

        String number = getRandomNumberString();
        Context context = new Context();
        context.setVariable("user", user);

        String process = templateEngine.process("mail", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Your code is " + number);
        helper.setText(process, true);
        helper.setTo(user.getLogin());
        javaMailSender.send(mimeMessage);
        return "Sent";
    }

}
