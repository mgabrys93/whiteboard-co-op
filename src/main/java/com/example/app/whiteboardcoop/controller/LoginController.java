package com.example.app.whiteboardcoop.controller;

import com.example.app.whiteboardcoop.util.SecurityContextAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private SecurityContextAccessor securityContextAccessor;

    @GetMapping("/")
    public String homeRedirect(){
        return "redirect:/whiteboard/list";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout){
        if(!securityContextAccessor.isCurrentAuthenticationAnonymous()) return "redirect:/whiteboard/list";
        if(error != null){
            model.addAttribute("error", "Invalid username and password!");
        }
        if(logout != null){
            model.addAttribute("logout", "You've been logged out successfully.");
        }
        return "login";
    }

}
