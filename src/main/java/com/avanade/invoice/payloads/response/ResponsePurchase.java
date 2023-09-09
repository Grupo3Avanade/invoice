package com.avanade.invoice.payloads.response;

import com.avanade.invoice.entities.enums.PurchaseStatus;
import java.math.BigDecimal;
import java.util.UUID;

public record ResponsePurchase(UUID cardId, String store, BigDecimal amount, PurchaseStatus purchaseStatus) {

}
