package com.avanade.invoice.payloads.response;

import java.util.UUID;
import com.avanade.invoice.entities.enums.CardType;

public record ResponseCard(UUID id,
                           String number,
                           String holderName,
                           CardType type,
                           Integer closingDay,
                           UUID userId) {

}
