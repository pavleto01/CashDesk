package com.fibank.cashdesk.model;

import com.fibank.cashdesk.model.enums.CurrencyType;

import java.util.EnumMap;

public class Cashier {
    private String name;
    private EnumMap<CurrencyType, CurrencyBalance> balances;

    public Cashier(String name, EnumMap<CurrencyType, CurrencyBalance> balances) {
        this.name = name;
        this.balances = balances;
    }

    public String getName() {
        return name;
    }

    public EnumMap<CurrencyType, CurrencyBalance> getBalances() {
        return balances;
    }

    public void setBalances(EnumMap<CurrencyType, CurrencyBalance> balances) {
        this.balances = balances;
    }
}
