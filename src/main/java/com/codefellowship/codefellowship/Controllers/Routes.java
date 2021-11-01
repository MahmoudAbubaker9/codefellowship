package com.codefellowship.codefellowship.Controllers;

import com.codefellowship.codefellowship.Repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Routes {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getRoot(){
        return "Home";
    }

//    @GetMapping("/signup")
//    public String getSignupPage(){
//
//        return "Signup";
//    }
//
//    @GetMapping("/login")
//    public String getLoginPage(){
//
//        return "Login";
//    }

}
