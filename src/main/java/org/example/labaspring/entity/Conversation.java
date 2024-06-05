package org.example.labaspring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "conversation")
public class Conversation extends TimestampEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "price_by_minute")
    private Double priceByMinute;

    @Column(name = "minutes")
    private Long minutes;
}
