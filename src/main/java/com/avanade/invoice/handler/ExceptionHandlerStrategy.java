package com.avanade.invoice.handler;

import com.avanade.invoice.payloads.response.ResponseStandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandlerStrategy {
    boolean supports(Exception exception);
    ResponseEntity<ResponseStandardError> handleException(Exception exception, HttpServletRequest request);
}
