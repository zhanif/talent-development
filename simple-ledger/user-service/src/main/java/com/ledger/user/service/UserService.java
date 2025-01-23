package com.ledger.user.service;

import com.ledger.user.dto.request.RegistrationRequest;
import com.ledger.user.dto.response.UserBalanceResponse;
import com.ledger.user.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse register(RegistrationRequest request);
    UserResponse getByEmail(String email);
    UserResponse getById(UUID id);
    UserBalanceResponse getBalanceById(UUID id);
}
