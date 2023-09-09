package com.avanade.invoice.payloads.response;

import java.time.LocalDate;
import java.util.UUID;
import com.avanade.invoice.entities.enums.CardType;

public record ResponseCard(UUID id,
                           String number,
                           String holderName,
//                           LocalDate expiry,
//                           String securityCode,
                           CardType type,
//                           String bankAccountId,
//                           Boolean isDependent,
                           Integer closingDay,
                           UUID userId) {

}
