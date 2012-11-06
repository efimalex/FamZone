package ru.familyportal.utils.email.templateEmail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Service class, generate message for client
 *
 * Created by Alex Efimov.
 * User: Саня
 * Date: 15.10.12
 * Time: 22:02
 */
@Service
public class MailTemplateService {
    private AbstractMailBuilder mailBuilder;

    public void setMailBuilder(AbstractMailBuilder mailBuilder){
        this.mailBuilder = mailBuilder;
    }

    public SimpleMailMessage getSimpleMailMEssage(){
       return mailBuilder.getMessage();
    }

    public void generateMassage(){
        mailBuilder.createNewMessage();
        mailBuilder.buildFrom();
        mailBuilder.buildTo();
        mailBuilder.buildSubject();
        mailBuilder.buildBody();
    }
}
