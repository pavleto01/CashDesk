package com.fibank.cashdesk.service;

import com.fibank.cashdesk.config.CashierInitializer;
import com.fibank.cashdesk.dto.CashBalanceResponse;
import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.CurrencyType;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CashBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(CashBalanceService.class);

    public List<CashBalanceResponse> getBalances(String cashierName, LocalDate from, LocalDate to) {
        List<CashBalanceResponse> responses = new ArrayList<>();

        Map<String, Cashier> allCashiers = CashierInitializer.CASHIERS;

        if (cashierName != null && !cashierName.isEmpty()) {
            Cashier cashier = allCashiers.get(cashierName);
            if (cashier != null) {
                addCashierBalancesToResponse(cashier, responses, from, to);
            }
        } else {
            for (Cashier c : allCashiers.values()) {
                addCashierBalancesToResponse(c, responses, from, to);
            }
        }

        return responses;
    }

    private void addCashierBalancesToResponse(Cashier cashier, List<CashBalanceResponse> responses,
                                              LocalDate from, LocalDate to) {
        for (Map.Entry<CurrencyType, CurrencyBalance> entry : cashier.getBalances().entrySet()) {
            CurrencyType currency = entry.getKey();
            CurrencyBalance balance = entry.getValue();

            List<String> transactions = (from != null || to != null)
                    ? readFilteredTransactions(cashier.getName(), from, to)
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

    private List<String> readFilteredTransactions(String cashier, LocalDate from, LocalDate to) {
        List<String> results = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/transactions.txt"))) {
            lines.forEach(line -> {
                try {
                    if (!line.contains(cashier)) return;

                    // example: 2025-04-15T20:41:53.543 | MARTINA | DEPOSIT | BGN | 600 | {10=10, 50=10}
                    String[] parts = line.split(" \\| ");
                    if (parts.length < 2) return;

                    String dateStr = parts[0].split("T")[0]; // Вземаме само датата
                    LocalDate txDate = LocalDate.parse(dateStr, formatter);

                    if ((from == null || !txDate.isBefore(from)) &&
                            (to == null || !txDate.isAfter(to))) {
                        results.add(line);
                    }
                } catch (Exception e) {
                    logger.warn("Skipped malformed transaction line: {}", line);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}
