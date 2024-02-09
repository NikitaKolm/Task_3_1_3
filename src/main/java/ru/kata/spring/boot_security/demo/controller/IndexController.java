package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @GetMapping(value = "/")
    public ModelAndView printWelcome() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("Welcome to Security, Boot, CRUD, MVC application");
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }
}