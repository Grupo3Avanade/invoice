package com.avanade.invoice.payloads.rabitmq;

import com.avanade.invoice.entities.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseDto {
    UUID id;
    BigDecimal amount;
    PurchaseStatus status;
    String createdAt;
    String store;
    UUID cardId;
}
