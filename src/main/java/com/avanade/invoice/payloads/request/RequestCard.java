package com.avanade.invoice.payloads.request;

import java.time.LocalDate;
import java.util.UUID;

public record RequestCard(
        String holderName,
//        Boolean isDependent,

        Integer closingDay,
        UUID userId,

        LocalDate expirationDate
        ) {

    //TODO field validations can be added if necessary
}
