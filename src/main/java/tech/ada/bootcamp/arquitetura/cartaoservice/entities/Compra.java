package tech.ada.bootcamp.arquitetura.cartaoservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "compra")
public class Compra {

    @Column(nullable = false, precision = 10, scale = 2) // TODO verificar precisao
    public BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "id_cartao", nullable = false)
    public Cartao cartao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusCompra statusCompra;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime dataCompra;

}