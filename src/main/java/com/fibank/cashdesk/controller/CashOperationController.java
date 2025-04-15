package com.fibank.cashdesk.controller;

import com.fibank.cashdesk.dto.CashOperationRequest;
import com.fibank.cashdesk.service.CashOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            @RequestHeader("FIB-X-AUTH") String apiKey,
            @RequestBody @Valid CashOperationRequest request
    ) {
        logger.info("Received {} operation from {} with {} {}",
                request.getOperationType(),
                request.getCashier(),
                request.getAmount(),
                request.getCurrency());

        return cashOperationService.processOperation(request);
    }
}
