package com.fibank.cashdesk.service;

import com.fibank.cashdesk.config.CashierInitializer;
import com.fibank.cashdesk.dto.CashBalanceResponse;
import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.CurrencyType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CashBalanceService {

    public List<CashBalanceResponse> getBalances(String cashierName) {
        List<CashBalanceResponse> responses = new ArrayList<>();

        Map<String, Cashier> allCashiers = CashierInitializer.CASHIERS;

        if (cashierName != null && !cashierName.isEmpty()) {
            Cashier cashier = allCashiers.get(cashierName);
            if (cashier != null) {
                addCashierBalancesToResponse(cashier, responses);
            }
        } else {
            for (Cashier c : allCashiers.values()) {
                addCashierBalancesToResponse(c, responses);
            }
        }

        return responses;
    }

    private void addCashierBalancesToResponse(Cashier cashier, List<CashBalanceResponse> responses) {
        for (Map.Entry<CurrencyType, CurrencyBalance> entry : cashier.getBalances().entrySet()) {
            CurrencyType currency = entry.getKey();
            CurrencyBalance balance = entry.getValue();
            responses.add(new CashBalanceResponse(
                    cashier.getName(),
                    currency,
                    balance.getTotal(),
                    balance.getDenominations()
            ));
        }
    }
}
