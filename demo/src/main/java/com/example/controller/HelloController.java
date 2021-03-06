package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String signIn(){
        return "login";
    }

    @GetMapping("/signUp")
    public String signUp(){
        return "register";
    }

    @GetMapping("/admin")
    public String admin() { return "admin"; }
}
