package com.example.p2pVoIP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

//    @GetMapping("/")
//    @ResponseBody
//    public String home(Model model) {
//        model.addAttribute("view", model);
//        return "index";
//    }
    @RequestMapping( "/")
    public String index() {
        return "index";
    }

}
