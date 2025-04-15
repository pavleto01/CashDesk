package com.fibank.cashdesk.repository;

import com.fibank.cashdesk.config.CashierInitializer;
import com.fibank.cashdesk.model.Cashier;
import com.fibank.cashdesk.model.CurrencyBalance;
import com.fibank.cashdesk.model.enums.CurrencyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Repository
public class TxtBalanceRepository {

    private static final Logger logger = LoggerFactory.getLogger(TxtBalanceRepository.class);

    @Value("${balances.file.path}")
    private String balancesFilePath;

    public void saveAllBalances() {
        try (FileWriter writer = new FileWriter(balancesFilePath)) {
            for (Cashier cashier : CashierInitializer.CASHIERS.values()) {
                writer.write("Cashier: " + cashier.getName() + System.lineSeparator());

                for (Map.Entry<CurrencyType, CurrencyBalance> entry : cashier.getBalances().entrySet()) {
                    CurrencyType currency = entry.getKey();
                    CurrencyBalance balance = entry.getValue();

                    writer.write("  Currency: " + currency + System.lineSeparator());
                    writer.write("  Total: " + balance.getTotal() + System.lineSeparator());
                    writer.write("  Denominations:" + System.lineSeparator());

                    for (Map.Entry<Integer, Integer> denom : balance.getDenominations().entrySet()) {
                        writer.write("    " + denom.getKey() + " -> " + denom.getValue() + System.lineSeparator());
                    }

                    writer.write(System.lineSeparator());
                }

                writer.write("-------------------------------------" + System.lineSeparator());
            }

            logger.info("Balances successfully saved to balances.txt");

        } catch (IOException e) {
            logger.error("Failed to write balances to file", e);
        }
    }
}
