package com.ledger.transaction.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    private ResponseEntity<?> generateResponse(Exception e, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();

        body.put("status", status.getReasonPhrase());
        body.put("code", status.value());
        body.put("message", getMessage(e, status));

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        log.error(String.format("\n\nMessage: \n%s\n\nClass:\n%s\n\nTrace:\n%s", e.getMessage(), e.getClass().getCanonicalName(), stackTrace));

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return generateResponse(e, getStatusFromException(e));
    }

    private HttpStatus getStatusFromException(Exception e) {
        if (e instanceof EntityNotFoundException) return HttpStatus.NOT_FOUND;
        else if (e instanceof EntityExistsException) return HttpStatus.CONFLICT;
        else if (e instanceof NoHandlerFoundException) return HttpStatus.NOT_FOUND;
        else if (e instanceof HttpMessageNotReadableException) return HttpStatus.NOT_ACCEPTABLE;
        else if (e instanceof HttpClientErrorException.BadRequest) return HttpStatus.BAD_REQUEST;
        else if (e instanceof MethodArgumentNotValidException) return HttpStatus.BAD_REQUEST;
        else if (e instanceof IllegalArgumentException) return HttpStatus.BAD_REQUEST;
        else if (e instanceof HttpRequestMethodNotSupportedException) return HttpStatus.METHOD_NOT_ALLOWED;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Object getMessage(Exception e, HttpStatus status) {
        Object message = e.getMessage();

        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            message = "Encountered internal error";
        }
        else if (e instanceof HttpMessageNotReadableException) {
            message = "Bad request message";
        }
//        else if (e instanceof MethodArgumentNotValidException) {
//            var errors = new HashMap<String, List<String>>();
//
//            for (ObjectError error : ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors()) {
//                FieldError fieldError = (FieldError) error;
//                String field = fieldError.getField();
//                errors.computeIfAbsent(field, k -> new ArrayList<>()).add(error.getDefaultMessage());
//            }
//
//            message = errors;
//        }
        else if (e instanceof MethodArgumentNotValidException) {
            var errors = new ArrayList<>();
            for (ObjectError error: ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors()) {
                FieldError fieldError = (FieldError) error;
                errors.add(fieldError.getField() + " " + error.getDefaultMessage());
            }
            message = errors;
        }
        return message;
    }
}
