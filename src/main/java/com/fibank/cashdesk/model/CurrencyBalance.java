package com.fibank.cashdesk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyBalance {
    private int total;
    private Map<Integer, Integer> denominations;
}
