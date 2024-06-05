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
@Table(name = "users")
public class User extends TimestampEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "money")
    private Double money;

    @Column(name = "password")
    private String password;

    @Column(name = "number")
    private String number;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<Conversation> conversations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Services> services = new ArrayList<>();

    public void addService(Services service) {
        if (service != null && !services.contains(service)) {
            this.services.add(service);
            service.getUsers().add(this);
        }
    }
}
