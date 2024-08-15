package com.example.exchangerate.enums;

public enum Currencys {
    USD("美元 USD"),
    CNY("人民幣 CNY"),
    JPY("日圓 JPY"),
    EUR("歐元 EUR"),
    AUD("澳幣 AUD");

    private final String displayName;

    Currencys(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
