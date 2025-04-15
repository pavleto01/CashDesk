package com.fibank.cashdesk.dto;

import com.fibank.cashdesk.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashBalanceResponse {
    private String cashier;
    private CurrencyType currency;
    private int total;
    private Map<Integer, Integer> denominations;
    private List<String> transactions;
}
