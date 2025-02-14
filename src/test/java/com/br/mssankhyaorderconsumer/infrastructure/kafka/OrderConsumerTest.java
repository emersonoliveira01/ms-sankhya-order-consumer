package com.br.mssankhyaorderconsumer.infrastructure.kafka;

import com.br.mssankhyaorderconsumer.domain.dto.OrderMessage;
import com.br.mssankhyaorderconsumer.domain.enums.OrderStatus;
import com.br.mssankhyaorderconsumer.domain.model.Order;
import com.br.mssankhyaorderconsumer.infrastructure.repository.OrderRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @InjectMocks
    private OrderConsumer orderConsumer;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private OrderRepository orderRepository;

    private String message;
    private OrderMessage orderMessage;

    @BeforeEach
    void setUp() {
        message = "{\"id\": 1, \"status\": {\"displayName\": \"PENDENTE DE SEPARAÇÃO\"}}";
        orderMessage = new OrderMessage();
        orderMessage.setId(1L);
        orderMessage.setStatus(OrderStatus.PENDENTE_DE_SEPARACAO);
    }

    @Test
    void consume_ShouldProcessOrder_WhenMessageIsValid() throws Exception {

        when(objectMapper.readValue(message, OrderMessage.class)).thenReturn(orderMessage);
        when(orderRepository.findById(orderMessage.getId())).thenReturn(Optional.of(new Order()));

        orderConsumer.consume(message);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void consume_ShouldThrowException_WhenOrderIsCancelled() throws Exception {
        orderMessage.setStatus(OrderStatus.CANCELADO);
        when(objectMapper.readValue(message, OrderMessage.class)).thenReturn(orderMessage);

        orderConsumer.consume(message);

        verify(objectMapper, times(1)).readValue(message, OrderMessage.class);
    }

    @Test
    void consume_ShouldLogError_WhenOrderNotFound() throws Exception {
        when(objectMapper.readValue(message, OrderMessage.class)).thenReturn(orderMessage);
        when(orderRepository.findById(orderMessage.getId())).thenReturn(Optional.empty());

        orderConsumer.consume(message);
        verify(orderRepository, times(1)).findById(orderMessage.getId());
    }

}