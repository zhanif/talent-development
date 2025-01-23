package com.ledger.user.controller;

import com.ledger.user.dto.request.RegistrationRequest;
import com.ledger.user.dto.response.UserResponse;
import com.ledger.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        var user = userService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String email) {
        UserResponse user = null;

        if (email != null && !email.isEmpty()) user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<?> getBalance(@PathVariable UUID id) { return ResponseEntity.ok(userService.getBalanceById(id)); }
}
