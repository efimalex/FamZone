package ru.familyportal.utils.email.templateEmail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import ru.familyportal.model.entity.User;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 15.10.12
 * Time: 21:38
 */
public abstract class AbstractMailBuilder {
    //@Autowired
    //protected VelocityEngine velocityEngine;

    private User user;

    protected SimpleMailMessage message;

    public SimpleMailMessage getMessage() {
        return message;
    }

    public void createNewMessage() {
        message = new SimpleMailMessage();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    protected abstract void buildFrom();

    protected abstract void buildReplyTo();

    protected abstract void buildTo();

    protected abstract void buildCc();

    protected abstract void buildBcc();

    protected abstract void buildSentDate();

    protected abstract void buildSubject();

    protected abstract void buildBody();
}
