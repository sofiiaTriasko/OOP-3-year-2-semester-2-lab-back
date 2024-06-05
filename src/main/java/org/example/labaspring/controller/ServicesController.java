package org.example.labaspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.labaspring.entity.Services;
import org.example.labaspring.repository.ServicesRepository;
import org.example.labaspring.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ServicesController {
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;

    @GetMapping("/services")
    public List<Services> getServices(@RequestParam(required = false) final Long paymentId) {
        return paymentId == null ? servicesRepository.findAll() : servicesRepository.findAllByPaymentId(paymentId);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam final Long userId,
                          @RequestParam final Set<Long> serviceIds) {
        userRepository.findById(userId).ifPresent(user -> {
            final List<Services> services = servicesRepository.findAllById(serviceIds);
            services.forEach(user::addService);
            userRepository.save(user);
        });
    }
}
