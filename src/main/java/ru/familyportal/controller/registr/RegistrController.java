package ru.familyportal.controller.registr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.familyportal.service.UserService;

import java.awt.image.BufferedImage;

/**
 * Created by Alex Efimov.
 * User: Саня
 * Date: 02.09.12
 * Time: 22:30
 */
@Controller
public class RegistrController {
    @Qualifier("userServiceImpl")
    @Autowired
    UserService service;

    private boolean userIsExist(final String userName){
        return service.hasUser(userName);
    }


}
