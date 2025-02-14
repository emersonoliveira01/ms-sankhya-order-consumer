package com.br.mssankhyaorderconsumer.infrastructure.kafka;

import com.br.mssankhyaorderconsumer.domain.dto.OrderMessage;
import com.br.mssankhyaorderconsumer.domain.enums.OrderStatus;
import com.br.mssankhyaorderconsumer.infrastructure.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "${kafka.orders.topic}", groupId = "order_group")
    public void consume(String message) {

        logger.info("Mensagem recebida: {}", message);

        try {
            var orderMessage = objectMapper.readValue(message, OrderMessage.class);
            var orderStatus = orderMessage.getStatus().getDisplayName();

            if (OrderStatus.CANCELADO.getDisplayName().equals(orderStatus) ||
                    OrderStatus.FINALIZADO.getDisplayName().equals(orderStatus)) {
                throw new ExpressionException("O pedido foi cancelado ou finalizado.");
            }

            var existingOrder = orderRepository.findById(orderMessage.getId())
                    .orElseThrow(() -> new ExpressionException("Pedido não encontrado."));

            existingOrder.setStatus(OrderStatus.FINALIZADO);
            existingOrder.setDateUpdateStatus(LocalDateTime.now());
            orderRepository.save(existingOrder);

            logger.info("Mensagem processada com sucesso: {}",orderMessage.getId());

        } catch (JsonProcessingException e) {
            logger.error("Erro ao processar a mensagem: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao atualizar o pedido: {}", e.getMessage());
        }
    }
}
