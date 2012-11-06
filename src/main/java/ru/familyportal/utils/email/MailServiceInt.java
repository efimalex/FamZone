package ru.familyportal.utils.email;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 04.10.12
 * Time: 22:56
 */
public interface MailServiceInt {
    void sendMail(SimpleMailMessage mailTemplate);
}
