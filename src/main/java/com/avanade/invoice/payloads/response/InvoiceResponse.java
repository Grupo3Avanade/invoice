package com.avanade.invoice.payloads.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public record InvoiceResponse(UUID id,
                              LocalDate processingDate,
                              LocalDate expirationDate,
                              Month month,

                              BigDecimal amount,
                              BigDecimal amountPaid,
                              List<ResponsePurchase> purchases) {

}
