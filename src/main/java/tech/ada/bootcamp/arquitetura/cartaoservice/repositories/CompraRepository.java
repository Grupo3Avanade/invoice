package tech.ada.bootcamp.arquitetura.cartaoservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.bootcamp.arquitetura.cartaoservice.entities.Compra;

import java.util.UUID;

@Repository
public interface CompraRepository extends JpaRepository<Compra, UUID> {
}