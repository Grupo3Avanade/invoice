package com.avanade.invoice.repositories;

import com.avanade.invoice.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByUserId(UUID id);

    List<Card> findAllByClosingDay(Integer date);

}