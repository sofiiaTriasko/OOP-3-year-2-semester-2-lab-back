package org.example.labaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDataDTO {
    private Long id;
    private PaymentDataDTO paymentDataDTO;
    private String name;
    private Double price;
}
