package com.bfhl.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BfhlService {

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    public List<Integer> getFibonacci(int n) {
        List<Integer> series = new ArrayList<>();
        if (n <= 0)
            return series;
        int a = 0, b = 1;
        series.add(a);
        if (n == 1)
            return series;
        series.add(b);
        for (int i = 2; i < n; i++) {
            int next = a + b;
            series.add(next);
            a = b;
            b = next;
        }
        return series;
    }

    public List<Integer> getPrimes(List<Integer> numbers) {
        return numbers.stream()
                .filter(this::isPrime)
                .collect(Collectors.toList());
    }

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public long getLCM(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        long res = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            res = lcm(res, numbers.get(i));
        }
        return res;
    }

    private long lcm(long a, long b) {
        return (a * b) / hcf(a, b);
    }

    public long getHCF(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        long res = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            res = hcf(res, numbers.get(i));
        }
        return res;
    }

    private long hcf(long a, long b) {
        while (b > 0) {
            a %= b;
            long temp = a;
            a = b;
            b = temp;
        }
        return a;
    }

    public String askAI(String question) {
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            return "API Key missing";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = GEMINI_URL + geminiApiKey;

        // Constructing a simple request body for Gemini
        // Prompting for a single-word answer as per requirement
        String prompt = "Answer in exactly one word: " + question;
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt)))));

        try {
            Map<String, Object> response = restTemplate.postForObject(url, requestBody, Map.class);
            // Parsing response to extract text (simplified)
            // Expecting candidates[0].content.parts[0].text
            List candidates = (List) response.get("candidates");
            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = (List) content.get("parts");
            Map part = (Map) parts.get(0);
            return ((String) part.get("text")).trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
