package ru.familyportal.utils.email.templateEmail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 15.10.12
 * Time: 21:47
 */
@Service
public class AccountActivationMailBuilder extends AbstractMailBuilder{
    @Autowired
    protected VelocityEngine velocityEngine;
    @Override
    protected void buildFrom() {
       message.setFrom("efimalex83@gmail.com");
    }

    @Override
    protected void buildReplyTo() {

    }

    @Override
    protected void buildTo() {
        message.setTo(getUser().getEmail());
    }

    @Override
    protected void buildCc() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void buildBcc() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void buildSentDate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void buildSubject() {
        message.setSubject("Acctivation account");
    }

    @Override
    protected void buildBody() {
        Map model = new HashMap();
        model.put("user", getUser());
        message.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "ru/familyportal/utils/email/emailTemplate/activationAccount.vm", model));
    }
}
