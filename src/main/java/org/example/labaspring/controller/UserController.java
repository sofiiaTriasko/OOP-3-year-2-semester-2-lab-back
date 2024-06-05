package org.example.labaspring.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.labaspring.dto.ConversationDataDTO;
import org.example.labaspring.dto.PaymentDataDTO;
import org.example.labaspring.dto.ServicesDataDTO;
import org.example.labaspring.dto.UserDataDTO;
import org.example.labaspring.entity.Conversation;
import org.example.labaspring.entity.Services;
import org.example.labaspring.entity.User;
import org.example.labaspring.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<UserDataDTO> getByNumber(@RequestParam String number) {
        final List<User> users = userRepository.findAllByNumberLike(number);
        final List<UserDataDTO> result = new ArrayList<>();

        for (User user : users) {
            final List<ServicesDataDTO> servicesData = getServicesDataDTOS(user);
            final List<ConversationDataDTO> conversationDataDTOS = getConversationDataDTOS(user);

            UserDataDTO userDataDTO = getUserDataDTO(user, conversationDataDTOS, servicesData);
            result.add(userDataDTO);
        }

        return result;
    }

    private UserDataDTO getUserDataDTO(User user, List<ConversationDataDTO> conversationDataDTOS, List<ServicesDataDTO> servicesData) {
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setId(user.getId());
        userDataDTO.setName(user.getName());
        userDataDTO.setMoney(user.getMoney());
        userDataDTO.setPassword(user.getPassword());
        userDataDTO.setNumber(user.getNumber());
        userDataDTO.setIsBlocked(user.getIsBlocked());
        userDataDTO.setConversations(conversationDataDTOS);
        userDataDTO.setServices(servicesData);
        return userDataDTO;
    }

    private List<ConversationDataDTO> getConversationDataDTOS(User user) {
        final List<ConversationDataDTO> conversationDataDTOS = new ArrayList<>();
        List<Conversation> conversations = user.getConversations();
        for (Conversation conversation : conversations) {
            ConversationDataDTO conversationDataDTO = new ConversationDataDTO();
            conversationDataDTO.setId(conversation.getId());
            conversationDataDTO.setPriceByMinute(conversationDataDTO.getPriceByMinute());
            conversationDataDTO.setMinutes(conversationDataDTO.getMinutes());
            conversationDataDTO.setPayment(conversation.getPayment() == null ? null : new PaymentDataDTO(conversation.getPayment().getId(), conversation.getPayment().getIsPaid()));
            conversationDataDTOS.add(conversationDataDTO);
        }
        return conversationDataDTOS;
    }

    private List<ServicesDataDTO> getServicesDataDTOS(User user) {
        final List<ServicesDataDTO> servicesData = new ArrayList<>();
        List<Services> services = user.getServices();
        for (Services service : services) {
            ServicesDataDTO servicesDataDTO = new ServicesDataDTO();
            servicesDataDTO.setId(service.getId());
            servicesDataDTO.setName(service.getName());
            servicesDataDTO.setPrice(service.getPrice());
            servicesDataDTO.setPaymentDataDTO(service.getPayment() == null ? null : new PaymentDataDTO(service.getPayment().getId(), service.getPayment().getIsPaid()));
            servicesData.add(servicesDataDTO);
        }
        return servicesData;
    }

    @PostMapping("/block-user")
    public void blockUser(@RequestParam("userId") Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        user.ifPresent(dbUser -> {
            dbUser.setIsBlocked(true);
            userRepository.save(dbUser);
        });
    }

    @PostMapping("/login")
    public ResponseData login(@RequestParam("name") String name) {
        final Optional<User> user = userRepository.findUserByName(name);
        return user.map(value ->
                ResponseData.builder()
                        .user(value)
                        .success(true)
                        .message("Success")
                        .build())
                .orElseGet(() -> ResponseData.builder().success(false).message("No user found").build());
    }

    @PostMapping("/register")
    public ResponseData register(@RequestParam("name") String name,
                         @RequestParam("password") String password,
                         @RequestParam("money") Double money) {
        final Optional<User> user = userRepository.findUserByName(name);
        if (user.isPresent()) {
            return ResponseData.builder().success(false).message("User exists").build();
        }
        User userToSave = new User(name, money, password, "", false, null, new ArrayList<>(), new ArrayList<>());
        User saved = userRepository.save(userToSave);
        return ResponseData.builder().user(saved).success(true).message("Success").build();
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class ResponseData {
        private User user;
        private boolean success;
        private String message;
    }
}
