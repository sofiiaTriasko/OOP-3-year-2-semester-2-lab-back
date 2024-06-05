package org.example.labaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
    private Long id;
    private String name;
    private Double money;
    private String password;
    private String number;
    private Boolean isBlocked;
    private List<ConversationDataDTO> conversations = new ArrayList<>();
    private List<ServicesDataDTO> services = new ArrayList<>();
}
