package com.fibank.cashdesk.controller;

import com.fibank.cashdesk.dto.CashBalanceResponse;
import com.fibank.cashdesk.service.CashBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cash-balance")
@RequiredArgsConstructor
public class CashBalanceController {

    private final CashBalanceService balanceService;

    @GetMapping
    public List<CashBalanceResponse> getBalance(
            @RequestHeader("FIB-X-AUTH") String apiKey,
            @RequestParam(required = false) String cashier,
            @RequestParam(required = false) String dateFrom, // засега не използваме
            @RequestParam(required = false) String dateTo
    ) {
        return balanceService.getBalances(cashier);
    }
}
