package ru.familyportal.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 04.10.12
 * Time: 21:01
 */
@Service
public class MailService implements MailServiceInt {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(SimpleMailMessage mailTemplate){
        if (mailTemplate == null){
            throw new IllegalArgumentException("Шаблон равен null!!!");
        }
        mailSender.send(mailTemplate);
    }

}
