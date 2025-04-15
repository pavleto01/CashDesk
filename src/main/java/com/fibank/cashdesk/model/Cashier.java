package com.fibank.cashdesk.model;

import com.fibank.cashdesk.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cashier {
    private String name;
    private EnumMap<CurrencyType, CurrencyBalance> balances;
}
