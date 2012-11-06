package ru.familyportal.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.familyportal.service.AuthenticationService;

import javax.faces.context.FacesContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: efim
 * Date: 12.05.12
 * Time: 10:45
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginBeanTest {
    /*@Mock
    private AuthenticationService authenticationService;

    @Mock
    private FacesContext context;

    @InjectMocks
    LoginBean loginBean = new LoginBean();

    @Test
    public void testSouccesLogin(){
        String userName = "admin";
        String password = "pswd";
        loginBean.setLogin(userName);
        loginBean.setPassword(password);
        when(authenticationService.login(userName, password)).thenReturn(true);
        assertEquals(loginBean.login(), "homePage.xhtml");
    }

    @Test
    public void testBadLogin(){
        String userName = "admin";
        String password = "pswd";
        loginBean.setLogin(userName);
        loginBean.setPassword(password);
        when(authenticationService.login(userName, password)).thenReturn(false);
        //when(context.getExternalContext().get).thenReturn(true);
        assertEquals(loginBean.login(), "wellcome.xhtml");
    }  */
}
