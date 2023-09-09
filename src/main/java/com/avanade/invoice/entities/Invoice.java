package com.avanade.invoice.entities;

import com.avanade.invoice.payloads.response.InvoiceResponse;
import com.avanade.invoice.payloads.response.ResponsePurchase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate processingDate;
    private LocalDate expirationDate;

    private BigDecimal amount;

    private BigDecimal amountPaid;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public InvoiceResponse toResponse() {
        List<ResponsePurchase> purchaseResponses = getPurchaseResponses();
        return new InvoiceResponse(
                id,
                processingDate,
                expirationDate,
                expirationDate.getMonth(),
                amount,
                amountPaid,
                purchaseResponses
        );
    }

    public List<ResponsePurchase> getPurchaseResponses() {
        return card.getPurchases().stream().map(Purchase::toResponse).toList();
    }
}
