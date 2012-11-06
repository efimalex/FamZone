package ru.familyportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.familyportal.model.entity.User;
import ru.familyportal.service.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: efim
 * Date: 30.05.12
 * Time: 15:25
 */
@Controller
public class LoginFormController {
    private User currentUser;

    @Qualifier("userServiceImpl")
    @Autowired
    UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView signUp(ModelMap model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/registration");
        mav.addObject("user",currentUser);
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUser(User user){
        currentUser = service.addAppUser(user);
       if (currentUser != null){
           return "auth/success";
       }
        return "#";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView seccuss(ModelMap model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("success");
        mav.addObject("user",currentUser);
        return mav;
    }
    /*@RequestMapping(value = "/register/selectLpu", method = RequestMethod.GET)
    public String selLpu(ModelMap model){
        return "/flows/register/selectLpu";
    }

    /* @RequestMapping(value = "/register/addPhoto", method = RequestMethod.GET)
    public String addPhoto(ModelMap model){
        return "/register/addPhoto";
    }  */


    //  @RequestMapping(method = RequestMethod.POST)
    //  public String register(@ModelAttribute("user")User user, BindingResult result){
    //    return null;
    //  }


}
