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
@Table(name = "payment")
public class Payment extends TimestampEntity {
    @Column(name = "is_paid")
    private Boolean isPaid;

    @ManyToOne
    private User user;

    @OneToOne
    private Conversation conversation;

    @OneToOne
    private Services services;
}
