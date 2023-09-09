package com.avanade.invoice.payloads.request;

import java.time.LocalDate;
import java.util.UUID;

public record RequestCard(
        String holderName,
        Integer closingDay,
        UUID userId,

        LocalDate expirationDate
        ) {
}
