package org.example.labaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDataDTO {
    private Long id;
    private Double priceByMinute;
    private Long minutes;
    private PaymentDataDTO payment;
}
