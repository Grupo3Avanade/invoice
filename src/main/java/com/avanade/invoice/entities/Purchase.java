package com.avanade.invoice.entities;

import com.avanade.invoice.payloads.rabitmq.CreatePurchaseDto;
import com.avanade.invoice.payloads.response.ResponsePurchase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import com.avanade.invoice.entities.enums.PurchaseStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = true)
    public UUID sharedId;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PurchaseStatus purchaseStatus;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private String store;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    public Card card;

    public Purchase() {

    }

    public Purchase(CreatePurchaseDto createPurchaseDto, Card card) {
        this.sharedId = createPurchaseDto.getId();
        this.amount = createPurchaseDto.getAmount();
        this.purchaseStatus = createPurchaseDto.getStatus();
        this.purchaseDate = this.convertDate(createPurchaseDto.getCreatedAt());
        this.store = createPurchaseDto.getStore();
        this.card = card;
    }

    private LocalDateTime convertDate(String createDateDto) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate ld = LocalDate.parse(createDateDto, dateformatter);
        LocalDateTime ldt = ld.atStartOfDay();
        return ldt;
    }

    public ResponsePurchase toResponse() {
        return new ResponsePurchase(card.getId(), store, amount, purchaseStatus);
    }
}
