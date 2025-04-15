package com.fibank.cashdesk.service;

import com.fibank.cashdesk.config.CashierInitializer;
import com.fibank.cashdesk.dto.CashOperationRequest;
import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.OperationType;
import com.fibank.cashdesk.repository.TxtBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CashOperationService {

    private static final Logger logger = LoggerFactory.getLogger(CashOperationService.class);
    private static final String TRANSACTIONS_FILE = "src/main/resources/transactions.txt";

    private final TxtBalanceRepository balanceRepository;

    public CashOperationService(TxtBalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public ResponseEntity<?> processOperation(CashOperationRequest request) {
        Cashier cashier = CashierInitializer.CASHIERS.get(request.getCashier());

        if (cashier == null) {
            logger.warn("Invalid cashier: {}", request.getCashier());
            return ResponseEntity.badRequest().body("Invalid cashier");
        }

        CurrencyBalance balance = cashier.getBalances().get(request.getCurrency());
        if (balance == null) {
            logger.warn("Invalid currency {} for cashier {}", request.getCurrency(), request.getCashier());
            return ResponseEntity.badRequest().body("Invalid currency");
        }

        int amount = request.getAmount();
        Map<Integer, Integer> denominations = request.getDenominations();

        if (request.getOperationType() == OperationType.DEPOSIT) {
            deposit(balance, denominations, amount);
            logger.info("Successful DEPOSIT for {}: {} {}", request.getCashier(), amount, request.getCurrency());
        } else if (request.getOperationType() == OperationType.WITHDRAWAL) {
            boolean success = withdraw(balance, denominations, amount);
            if (!success) {
                logger.warn("FAILED withdrawal for {}: amount={} {} - insufficient denominations",
                        request.getCashier(), amount, request.getCurrency());
                return ResponseEntity.badRequest().body("Invalid withdrawal denominations");
            }
            logger.info("Successful WITHDRAWAL for {}: {} {}", request.getCashier(), amount, request.getCurrency());
        }

        logTransaction(request);
        balanceRepository.saveAllBalances();

        return ResponseEntity.ok("Operation completed successfully");
    }

    private void deposit(CurrencyBalance balance, Map<Integer, Integer> depositDenoms, int amount) {
        balance.setTotal(balance.getTotal() + amount);
        for (Map.Entry<Integer, Integer> entry : depositDenoms.entrySet()) {
            balance.getDenominations().merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    private boolean withdraw(CurrencyBalance balance, Map<Integer, Integer> withdrawDenoms, int amount) {
        int currentTotal = balance.getTotal();
        if (amount > currentTotal) return false;

        //Validation if there is enough quantity of each nomination
        for (Map.Entry<Integer, Integer> entry : withdrawDenoms.entrySet()) {
            int denomination = entry.getKey();
            int requestedQty = entry.getValue();
            int availableQty = balance.getDenominations().getOrDefault(denomination, 0);
            if (requestedQty > availableQty) {
                return false;
            }
        }

        //Denomination update
        balance.setTotal(currentTotal - amount);
        for (Map.Entry<Integer, Integer> entry : withdrawDenoms.entrySet()) {
            int denomination = entry.getKey();
            int qty = entry.getValue();
            balance.getDenominations().put(denomination,
                    balance.getDenominations().get(denomination) - qty);
        }

        return true;
    }

    private void logTransaction(CashOperationRequest request) {
        String record = String.format(
                "%s | %s | %s | %s | %d | %s%n",
                LocalDateTime.now(),
                request.getCashier(),
                request.getOperationType(),
                request.getCurrency(),
                request.getAmount(),
                request.getDenominations()
        );

        try (FileWriter writer = new FileWriter(TRANSACTIONS_FILE, true)) {
            writer.write(record);
        } catch (IOException e) {
            logger.error("Failed to write to transactions.txt", e);
        }
    }
}
