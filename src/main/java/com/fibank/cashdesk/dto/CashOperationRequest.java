package com.fibank.cashdesk.dto;

import com.fibank.cashdesk.model.enums.CurrencyType;
import com.fibank.cashdesk.model.enums.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class CashOperationRequest {

    @NotBlank
    private String cashier;

    @NotNull
    private OperationType operationType;

    @NotNull
    private CurrencyType currency;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    private Map<Integer, Integer> denominations;

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    public void setDenominations(Map<Integer, Integer> denominations) {
        this.denominations = denominations;
    }
}
