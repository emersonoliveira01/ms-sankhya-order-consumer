package com.br.mssankhyaorderconsumer.domain.dto;


import com.br.mssankhyaorderconsumer.domain.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMessage {

    private Long id;

    private OrderStatus status;
}
