package com.fibank.cashdesk.dto;

import com.fibank.cashdesk.model.enums.CurrencyType;

import java.util.Map;

public class CashBalanceResponse {
    private String cashier;
    private CurrencyType currency;
    private int total;
    private Map<Integer, Integer> denominations;

    public CashBalanceResponse(String cashier, CurrencyType currency, int total, Map<Integer, Integer> denominations) {
        this.cashier = cashier;
        this.currency = currency;
        this.total = total;
        this.denominations = denominations;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    public void setDenominations(Map<Integer, Integer> denominations) {
        this.denominations = denominations;
    }
}
