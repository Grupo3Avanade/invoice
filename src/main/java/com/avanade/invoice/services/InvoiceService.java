package com.avanade.invoice.services;

import com.avanade.invoice.entities.Card;
import com.avanade.invoice.entities.Purchase;
import com.avanade.invoice.entities.enums.PurchaseStatus;
import com.avanade.invoice.exceptions.DatabaseException;
import com.avanade.invoice.exceptions.ResourceNotFoundException;
import com.avanade.invoice.payloads.response.InvoiceResponse;
import com.avanade.invoice.repositories.InvoiceRepository;
import com.avanade.invoice.entities.Invoice;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    private Invoice findOrFail(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice could not be found"));
    }

    private List<Invoice> findInvoicesByUserId(UUID userId) {
        return repository.findAllByUserId(userId);
    }


    public InvoiceResponse findById(UUID id) {
        Invoice invoice = findOrFail(id);
        return invoice.toResponse();
    }

    public List<InvoiceResponse> findAllByUser(UUID userId) {
        List<Invoice> userInvoices = findInvoicesByUserId(userId);
        return userInvoices.stream().map(Invoice::toResponse).toList();
    }

    public void createInvoicesByCards(List<Card> cards) {
        List<Invoice> invoices = new ArrayList<>();

        for (Card card : cards) {
            Invoice newInvoice = prepareInvoice(card);
            List<Purchase> monthPurchases = getMonthPurchases(card);
            setInvoiceAmount(monthPurchases, newInvoice);
            invoices.add(newInvoice);
        }

        saveInvoices(invoices);
    }

    private Invoice prepareInvoice(Card card) {
        Invoice newInvoice = new Invoice();
        newInvoice.setExpirationDate(getInvoiceExpirationDateToPay());
        newInvoice.setAmountPaid(BigDecimal.ZERO);
        newInvoice.setCard(card);
        newInvoice.setAmount(BigDecimal.ZERO);
        newInvoice.setProcessingDate(LocalDate.now());
        return newInvoice;
    }

    private void setInvoiceAmount(List<Purchase> monthPurchases, Invoice newInvoice) {
        BigDecimal totalAmount = sumAmount(monthPurchases);
        newInvoice.setAmount(totalAmount);
    }

    private BigDecimal sumAmount(List<Purchase> purchases) {
        return purchases.stream().map(Purchase::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<Purchase> getMonthPurchases(Card card) {
        LocalDateTime invoiceBeginDate = getInvoiceBeginDate();
        LocalDateTime invoiceExpirationDate = getInvoiceExpirationDate();
        return card.getPurchases()
                .stream()
                .filter(purchase -> isPurchaseWithinMonthApproved(purchase, invoiceBeginDate, invoiceExpirationDate))
                .toList();
    }

    private boolean isPurchaseWithinMonthApproved(Purchase purchase, LocalDateTime beginDate, LocalDateTime endDate) {
        LocalDateTime purchaseDate = purchase.getPurchaseDate();
        return purchaseDate.isAfter(beginDate) && purchaseDate.isBefore(endDate) && purchase.purchaseStatus.equals(PurchaseStatus.APPROVED);
    }

    // Retorna a data de fechamento da fatura (dia atual do fechamento da fatura).
    private LocalDateTime getInvoiceExpirationDate() {
        return LocalDate.now().atStartOfDay();
    }

    // Retorna a data de início da fatura (um mês antes da data de fechamento da fatura).
    private LocalDateTime getInvoiceBeginDate() {
        return getInvoiceExpirationDate().minusMonths(1);
    }

    // Retorna a data para pagamento da fatura (uma semana após o fechamento da fatura).
    private LocalDate getInvoiceExpirationDateToPay() {
        return getInvoiceExpirationDate().plusWeeks(1).toLocalDate();
    }

    private void saveInvoices(List<Invoice> invoices) {
        try {
            repository.saveAllAndFlush(invoices);
        } catch (Exception e) {
            throw new DatabaseException("Error while saving invoices");
        }
    }
}
