package com.fibank.cashdesk.dto;

import com.fibank.cashdesk.model.enums.CurrencyType;
import com.fibank.cashdesk.model.enums.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class CashOperationRequest {

    @NotBlank
    private String cashier;

    @NotNull
    private OperationType operationType;

    @NotNull
    private CurrencyType currency;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    private Map<Integer, Integer> denominations;
}
