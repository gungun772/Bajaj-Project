package com.bfhl.api.controller;

import com.bfhl.api.dto.BfhlRequestDTO;
import com.bfhl.api.dto.BfhlResponseDTO;
import com.bfhl.api.service.BfhlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class BfhlController {

    private final BfhlService bfhlService;
    private final String officialEmail = "gungun@chitkara.edu.in";

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponseDTO> handlePost(@RequestBody BfhlRequestDTO request) {
        Object data;
        int keysFound = 0;

        if (request.getFibonacci() != null) {
            data = bfhlService.getFibonacci(request.getFibonacci());
            keysFound++;
        } else if (request.getPrime() != null) {
            data = bfhlService.getPrimes(request.getPrime());
            keysFound++;
        } else if (request.getLcm() != null) {
            data = bfhlService.getLCM(request.getLcm());
            keysFound++;
        } else if (request.getHcf() != null) {
            data = bfhlService.getHCF(request.getHcf());
            keysFound++;
        } else if (request.getAI() != null) {
            data = bfhlService.askAI(request.getAI());
            keysFound++;
        } else {
            return ResponseEntity.badRequest()
                    .body(new BfhlResponseDTO(false, officialEmail, "No valid key found in request body"));
        }

        if (keysFound > 1) {
            return ResponseEntity.badRequest()
                    .body(new BfhlResponseDTO(false, officialEmail, "Exactly one key must be present"));
        }

        return ResponseEntity.ok(new BfhlResponseDTO(true, officialEmail, data));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> returnNoFavicon() {
        return ResponseEntity.noContent().build();
    }
}
