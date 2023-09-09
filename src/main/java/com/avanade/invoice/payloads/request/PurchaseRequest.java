package com.avanade.invoice.payloads.request;

import com.avanade.invoice.entities.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseRequest(
        UUID cardId,
        String store,
        BigDecimal amount,
        PaymentMethod method
) {
}
