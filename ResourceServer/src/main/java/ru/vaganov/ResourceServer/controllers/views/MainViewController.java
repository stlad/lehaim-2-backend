package ru.vaganov.ResourceServer.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainViewController {


    @GetMapping("/")
    public String getMainPatientsPage(){
        return "patients";
    }

    @GetMapping("/tests/{id}")
    public String getMainPatientsPage(@PathVariable Long id){
        return "onco_test";
    }
}
