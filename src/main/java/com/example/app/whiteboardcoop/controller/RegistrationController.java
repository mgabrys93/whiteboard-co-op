package com.example.app.whiteboardcoop.controller;

import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.service.UserService;
import com.example.app.whiteboardcoop.util.SecurityContextAccessor;
import com.example.app.whiteboardcoop.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private SecurityContextAccessor securityContextAccessor;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @GetMapping
    public String register(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        if(securityContextAccessor.isCurrentAuthenticationAnonymous()) {
            registrationValidator.validate(userForm, bindingResult);
            if(bindingResult.hasErrors()){
                return "registration";
            }
            userService.save(userForm);
        }
        return "redirect:/home";
    }

}
