package br.com.fiap.mensageria.controller;

import br.com.fiap.mensageria.model.OrderCreatedMessage;
import br.com.fiap.mensageria.service.OrderPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderPublisher orderPublisher;

    public OrderController(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderCreatedMessage message) {
        // Publica a mensagem no broker
        orderPublisher.publishOrderCreated(message);
        // Retorna resposta simples para o cliente
        return ResponseEntity.ok("Order message published: " + message.orderId());
    }
}