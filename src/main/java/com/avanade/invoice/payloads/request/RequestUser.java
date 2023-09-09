package com.avanade.invoice.payloads.request;

import java.util.UUID;

public record RequestUser(

        UUID sharedId,
        String name
) {
}
