package com.example.kursach2tkp.controllers;

import com.example.kursach2tkp.models.User;
import com.example.kursach2tkp.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping()
public class AuthController {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication){
        return "auth/логин";
    }

    @GetMapping("/registration")
    public String registration(){
        return "auth/регистрация";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(value = "login") String login,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "email") String email,
                          Model model){

        User newUser = new User();
        newUser.setLogin(login.toLowerCase(Locale.ROOT));
        newUser.setPassword(password);
        newUser.setEmail(email.toLowerCase(Locale.ROOT));
        newUser.setRole("USER");

        if(!userDetailsService.saveUser(newUser)){
            return "redirect:/registration?collision_error";
        }
        return "redirect:/disciplines";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (auth != null) {
              new SecurityContextLogoutHandler().logout(request, response, auth);
          }

          return "redirect:/disciplines";
      }
}
