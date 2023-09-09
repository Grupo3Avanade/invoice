package com.avanade.invoice.entities;

import com.avanade.invoice.payloads.response.ResponseCard;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.avanade.invoice.entities.enums.CardType;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 16)
    private String number;

    @Column(nullable = false)
    private String holderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;

    @Column(nullable = false)
    private Integer closingDay;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Purchase> purchases;

    public ResponseCard toResponse() {
        return new ResponseCard(
                this.id,
                this.number,
                this.holderName,
                this.type,
                this.closingDay,
                this.user.getId()
        );
    }
}