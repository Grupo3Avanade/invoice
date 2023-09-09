package com.avanade.invoice.payloads.request;

import com.avanade.invoice.entities.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseRequest(
//        @NotNull
        UUID cardId,
//        @NotNull
        String store,
//        @NotNull
        BigDecimal amount,
//        @NotNull
        PaymentMethod method
) {
}
