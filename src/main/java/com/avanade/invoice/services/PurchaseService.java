package com.avanade.invoice.services;

import com.avanade.invoice.entities.Card;
import com.avanade.invoice.entities.Purchase;
import com.avanade.invoice.payloads.rabitmq.CreatePurchaseDto;
import com.avanade.invoice.payloads.response.ResponsePurchase;
import org.springframework.stereotype.Service;
import com.avanade.invoice.repositories.PurchaseRepository;
import com.avanade.invoice.exceptions.DatabaseException;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;
    private final CardService cardService;

    public PurchaseService(PurchaseRepository repository, CardService cardService) {
        this.repository = repository;
        this.cardService = cardService;
    }

    public ResponsePurchase execute(CreatePurchaseDto createPurchaseDto) {
        Card card = getCard(createPurchaseDto);
        Purchase purchase = new Purchase(createPurchaseDto, card);
        saveOrFail(purchase);
        return purchase.toResponse();
    }

    private void saveOrFail(Purchase purchase) {
        try {
            repository.save(purchase);
        } catch (Exception e) {
            throw new DatabaseException("Error while saving purchase");
        }
    }

    private Card getCard(CreatePurchaseDto purchaseRequest) {
        return cardService.findOrFailById(purchaseRequest.getCardId());
    }
}
