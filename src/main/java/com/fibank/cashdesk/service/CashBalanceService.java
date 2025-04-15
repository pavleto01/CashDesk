package com.fibank.cashdesk.service;

import com.fibank.cashdesk.config.CashierInitializer;
import com.fibank.cashdesk.dto.CashBalanceResponse;
import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for calculating cash balances and retrieving transaction history.
 *
 * Balances are retrieved from in-memory initialized cashiers. Transaction history is read
 * from a local file (default: transactions.txt), filtered optionally by date range.
 *
 */
@Service
@RequiredArgsConstructor
public class CashBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(CashBalanceService.class);

    private final TransactionService transactionService;

    public List<CashBalanceResponse> getBalances(String cashierName, LocalDate from, LocalDate to) {
        List<CashBalanceResponse> responses = new ArrayList<>();
        Map<String, Cashier> allCashiers = CashierInitializer.CASHIERS;

        if (cashierName != null && !cashierName.isBlank()) {
            Cashier cashier = allCashiers.get(cashierName);
            if (cashier != null) {
                addCashierBalancesToResponse(cashier, responses, from, to);
            } else {
                logger.warn("Requested cashier '{}' not found", cashierName);
            }
        } else {
            allCashiers.values().forEach(c -> addCashierBalancesToResponse(c, responses, from, to));
        }

        return responses;
    }

    private void addCashierBalancesToResponse(Cashier cashier, List<CashBalanceResponse> responses,
                                              LocalDate from, LocalDate to) {
        for (Map.Entry<CurrencyType, CurrencyBalance> entry : cashier.getBalances().entrySet()) {
            CurrencyType currency = entry.getKey();
            CurrencyBalance balance = entry.getValue();

            List<String> transactions = (from != null || to != null)
                    ? transactionService.readFilteredTransactions(cashier.getName(), from, to)
                    : List.of();

            responses.add(new CashBalanceResponse(
                    cashier.getName(),
                    currency,
                    balance.getTotal(),
                    balance.getDenominations(),
                    transactions
            ));
        }
    }
}
