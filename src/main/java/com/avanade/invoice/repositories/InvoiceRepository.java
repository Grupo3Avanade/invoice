package com.avanade.invoice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.avanade.invoice.entities.Invoice;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query("SELECT f FROM Invoice f WHERE f.card.user.id = :idUser")
    List<Invoice> findAllByUserId(UUID idUser);
}