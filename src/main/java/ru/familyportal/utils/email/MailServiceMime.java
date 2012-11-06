package ru.familyportal.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 22.10.12
 * Time: 20:47
 */
@Service
public class MailServiceMime implements MailServiceInt, Runnable{
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(final SimpleMailMessage mailTemplate) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(mailTemplate.getTo());
                message.setFrom(mailTemplate.getFrom());
                message.setSubject(mailTemplate.getSubject());
                message.setText(mailTemplate.getText(),true);
            }
        };
        mailSender.send(preparator);
    }

    public void run() {


    }
}
