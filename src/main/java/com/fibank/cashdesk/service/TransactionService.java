package com.fibank.cashdesk.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Reads transaction history for a cashier from a plain text file,
 * optionally filtered by date range.
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Value("${transactions.file.path}")
    private String transactionFilePath;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public List<String> readFilteredTransactions(String cashier, LocalDate from, LocalDate to) {
        List<String> results = new ArrayList<>();
        Path path = Path.of(transactionFilePath);

        if (!Files.exists(path)) {
            logger.error("Transaction file not found: {}", transactionFilePath);
            return List.of();
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                try {
                    if (!line.contains(cashier)) return;

                    String[] parts = line.split(" \\| ");
                    if (parts.length < 2) return;

                    String dateStr = parts[0].split("T")[0];
                    LocalDate txDate = LocalDate.parse(dateStr, DATE_FORMATTER);

                    if (isInRange(txDate, from, to)) {
                        results.add(line);
                    }
                } catch (Exception e) {
                    logger.warn("Skipped malformed transaction line: {}", line);
                }
            });
        } catch (IOException e) {
            logger.error("Error reading transaction file", e);
        }

        return results;
    }

    private boolean isInRange(LocalDate date, LocalDate from, LocalDate to) {
        return (from == null || !date.isBefore(from)) &&
                (to == null || !date.isAfter(to));
    }
}
