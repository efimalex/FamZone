package ru.familyportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.familyportal.service.AuthenticationService;


/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 11.05.12
 * Time: 19:52
 */
@Controller

public class LoginBean {

    @Qualifier("authenticationServiceImpl")
    @Autowired
    private AuthenticationService authenticationService;

    private String login;
    private String password;

    public String login() {
        boolean soccess = authenticationService.login(login, password);

        if (soccess) {
            return "homePage.xhtml";
        } else {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login or password incorrect."));
            return "wellcome.xhtml";
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
