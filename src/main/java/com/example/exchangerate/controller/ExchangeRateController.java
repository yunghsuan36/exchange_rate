package com.example.exchangerate.controller;

import com.example.exchangerate.enums.Currencys;
import com.example.exchangerate.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/getRates")
    public List<Map<String, String>> getRates(@RequestParam String currency) {
        Currencys selectedCurrency = Currencys.valueOf(currency);
        return exchangeRateService.getExchangeRate(selectedCurrency);
    }

}

