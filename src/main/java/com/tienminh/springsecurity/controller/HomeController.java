package com.tienminh.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('ADMIN')")
    String hello() {
        return "/hello";
    }

    @GetMapping("/")
    String home() {
        return "/home";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

}
