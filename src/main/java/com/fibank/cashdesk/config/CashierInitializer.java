package com.fibank.cashdesk.config;

import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.CurrencyType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CashierInitializer {

    public static final Map<String, Cashier> CASHIERS = new HashMap<>();

    @PostConstruct
    public void initCashiers() {
        if (!CASHIERS.isEmpty()) {
            return; // prevent duplicate initialization
        }

        CASHIERS.put("MARTINA", createCashier("MARTINA"));
        CASHIERS.put("PETER", createCashier("PETER"));
        CASHIERS.put("LINDA", createCashier("LINDA"));
    }

    private Cashier createCashier(String name) {
        EnumMap<CurrencyType, CurrencyBalance> balances = new EnumMap<>(CurrencyType.class);

        // BGN: 1000 лв (50x10, 10x50)
        Map<Integer, Integer> bgnDenominations = new HashMap<>();
        bgnDenominations.put(50, 10);
        bgnDenominations.put(10, 50);
        balances.put(CurrencyType.BGN, new CurrencyBalance(1000, bgnDenominations));

        // EUR: 2000 € (100x10, 20x50)
        Map<Integer, Integer> eurDenominations = new HashMap<>();
        eurDenominations.put(100, 10);
        eurDenominations.put(20, 50);
        balances.put(CurrencyType.EUR, new CurrencyBalance(2000, eurDenominations));

        return new Cashier(name, balances);
    }
}
