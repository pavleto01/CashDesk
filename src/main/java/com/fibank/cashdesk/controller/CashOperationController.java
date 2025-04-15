package com.fibank.cashdesk.controller;

import com.fibank.cashdesk.dto.CashOperationRequest;
import com.fibank.cashdesk.service.CashOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cash-operation")
@RequiredArgsConstructor
public class CashOperationController {

    private static final Logger logger = LoggerFactory.getLogger(CashOperationController.class);

    private final CashOperationService cashOperationService;

    @PostMapping
    public ResponseEntity<?> handleCashOperation(
            @RequestBody @Valid CashOperationRequest request
    ) {
        logger.info("Received {} operation from {} with {} {} and denominations {}",
                request.getOperationType(),
                request.getCashier(),
                request.getAmount(),
                request.getCurrency(),
                request.getDenominations());

        return ResponseEntity.status(HttpStatus.CREATED).body(cashOperationService.processOperation(request));
    }
}
