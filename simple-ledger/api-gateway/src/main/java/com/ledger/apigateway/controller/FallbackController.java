package com.ledger.apigateway.controller;

import com.ledger.apigateway.dto.response.FallbackResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<?> fallback() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FallbackResponse("Service is unavailable, please try again later."));
    }
}
