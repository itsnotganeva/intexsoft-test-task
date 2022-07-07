package by.ganevich.mail;

import by.ganevich.dto.RegistrationRequestDto;
import by.ganevich.entity.User;
import by.ganevich.entity.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final TemplateEngine templateEngine;
    private final String subject = "BankSystem security verification";

    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.properties.mail.smtp.auth}")
    private Boolean auth;
    @Value("${mail.properties.mail.smtp.starttls.enable}")
    private Boolean starttls;
    @Value("${isEmailSenderActive}")
    private Boolean isEmailSenderActive;

    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        return mailSender;
    }

    private static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public String sendEmail(RegistrationRequestDto user) throws MessagingException {

        String number = getRandomNumberString();
        Context context = new Context();

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", user);
        variables.put("number", number);

        context.setVariables(variables);
        String process = templateEngine.process("mail", context);

        MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(user.getLogin());
        getJavaMailSender().send(mimeMessage);
        return number;
    }

    public void invokeEmailSender(User user, RegistrationRequestDto registrationRequest) throws MessagingException {
        if (isEmailSenderActive) {
            user.setCode(sendEmail(registrationRequest));
        } else {
            user.setState(State.ACTIVATED);
        }
    }

}
