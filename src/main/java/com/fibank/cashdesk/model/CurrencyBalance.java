package com.fibank.cashdesk.model;

import java.util.Map;

public class CurrencyBalance {
    private int total;
    private Map<Integer, Integer> denominations; // Номинал -> брой

    public CurrencyBalance(int total, Map<Integer, Integer> denominations) {
        this.total = total;
        this.denominations = denominations;
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
