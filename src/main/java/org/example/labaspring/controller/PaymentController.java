package org.example.labaspring.controller;

import lombok.RequiredArgsConstructor;
import org.example.labaspring.entity.Conversation;
import org.example.labaspring.entity.Payment;
import org.example.labaspring.entity.Services;
import org.example.labaspring.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final PaymentRepository paymentRepository;

    @GetMapping("/payments")
    public List<Payment> getPayments(@RequestParam boolean isPad) {
        return paymentRepository.findPaymentsByIsPaid(isPad);
    }


    @PostMapping("/payment")
    public void pay(@RequestParam Long paymentId,
                    @RequestParam Double sum) {
        final Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        double totalCost = 0.0;
        Conversation conversation = payment.getConversation();
        if (conversation != null) {
            totalCost += conversation.getMinutes() * conversation.getPriceByMinute();
        }

        Services service = payment.getServices();
        if (service != null) {
            totalCost += service.getPrice();
        }

        boolean isPaid = sum >= totalCost;
        payment.setIsPaid(isPaid);
        paymentRepository.save(payment);
    }
}
