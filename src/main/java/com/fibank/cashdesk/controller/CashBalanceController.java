package com.fibank.cashdesk.controller;

import com.fibank.cashdesk.dto.CashBalanceResponse;
import com.fibank.cashdesk.service.CashBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cash-balance")
@RequiredArgsConstructor
public class CashBalanceController {

    private final CashBalanceService balanceService;

    @GetMapping
    public List<CashBalanceResponse> getBalance(
            @RequestParam(required = false) String cashier,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        return balanceService.getBalances(cashier, dateFrom, dateTo);
    }


}
