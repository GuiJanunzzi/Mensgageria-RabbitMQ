package br.com.fiap.mensageria.model;

public record OrderItem(
        String productId,
        int quantity
) {}