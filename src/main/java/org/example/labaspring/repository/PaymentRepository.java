package org.example.labaspring.repository;

import org.example.labaspring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findPaymentsByIsPaid(boolean isPaid);
}
