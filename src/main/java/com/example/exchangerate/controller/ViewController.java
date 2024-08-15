package com.example.exchangerate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/forexRate")
    public String getForexRates() {
        return "forexChart";
    }
}
