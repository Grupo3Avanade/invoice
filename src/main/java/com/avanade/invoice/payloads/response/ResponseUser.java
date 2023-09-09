package com.avanade.invoice.payloads.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record ResponseUser(
        UUID id,
        UUID sharedId,
        String name,
        Date createdAt,
        Date updatedAt) {
}
