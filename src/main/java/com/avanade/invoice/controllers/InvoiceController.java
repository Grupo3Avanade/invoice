package com.avanade.invoice.controllers;

import com.avanade.invoice.payloads.response.InvoiceResponse;
import com.avanade.invoice.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getById(@PathVariable("id") UUID id) {
        InvoiceResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllByUserId(@RequestParam UUID idUser) {
        List<InvoiceResponse> response = service.findAllByUser(idUser);
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    public ResponseEntity<List<InvoiceResponse>> getAllByCardId(@RequestParam UUID idCard) {
//        List<InvoiceResponse> response = service.findAllByCard(idCard);
//        return ResponseEntity.ok(response);
//    }
}
