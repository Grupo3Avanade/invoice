package com.avanade.invoice.external.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.avanade.invoice.payloads.rabitmq.CreatePurchaseDto;
import com.avanade.invoice.services.PurchaseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseConsumer {
    private final PurchaseService purchaseService;

    @RabbitListener(queues = "${business.in.create-invoice}")
    public void consume(@Payload CreatePurchaseDto dto) {
        purchaseService.execute(dto);
    }
}