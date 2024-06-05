package org.example.labaspring.repository;

import org.example.labaspring.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {
    List<Services> findAllByPaymentId(Long paymentId);
}
