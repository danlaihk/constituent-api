package io.danlaihk.webApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class WebController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
}
