package com.group.foodservice.controller;

import com.group.foodservice.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/")
    public String root(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        return "/index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "/html/login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "/html/register.html";
    }

    @GetMapping("/fillInfo")
    public String fillInfo(){
        return "/html/fillInfo.html";
    }
}
