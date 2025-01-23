package com.ledger.user.service.impl;

import com.ledger.user.dto.request.RegistrationRequest;
import com.ledger.user.dto.response.UserBalanceResponse;
import com.ledger.user.dto.response.UserResponse;
import com.ledger.user.model.User;
import com.ledger.user.repository.UserRepository;
import com.ledger.user.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate rest;

    @Value("${server.gateway.url}")
    private String gatewayUrl;

    @Override
    public UserResponse register(RegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new EntityExistsException("Email sudah digunakan");
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .build();

        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getByEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email), UserResponse.class);
    }

    @Override
    public UserResponse getById(UUID id) {
        return modelMapper.map(userRepository.findById(id), UserResponse.class);
    }

    @Override
    public UserBalanceResponse getBalanceById(UUID id) {
        BigInteger balance = rest.getForEntity(this.gatewayUrl + "/event-service/events/currentBalance/" + id.toString(), BigInteger.class).getBody();
        if (balance == null) balance = BigInteger.ZERO;

        return new UserBalanceResponse(id.toString(), balance);
    }
}
