package com.bfhl.api.exception;

import com.bfhl.api.dto.BfhlResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final String officialEmail = "gungun@chitkara.edu.in";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BfhlResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "Validation failed: " + ex.getBindingResult().getFieldError().getDefaultMessage();
        BfhlResponseDTO response = new BfhlResponseDTO(false, officialEmail, errorMessage);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BfhlResponseDTO> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        BfhlResponseDTO response = new BfhlResponseDTO(false, officialEmail, "Malformed JSON request");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponseDTO> handleAllExceptions(Exception ex) {
        BfhlResponseDTO response = new BfhlResponseDTO(false, officialEmail,
                "Internal Server Error: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
