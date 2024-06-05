package org.example.labaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "service")
public class Services extends TimestampEntity {
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private String name;
    private Double price;

    @ManyToMany
    private List<User> users = new ArrayList<>();
}
