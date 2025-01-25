package org.project.apigatewaybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class GatewayController {

    private final RestTemplate restTemplate;

    @GetMapping("/books/book-storage/{endpoint}")
    public ResponseEntity<?> proxyToBookStorage(@PathVariable String endpoint, @RequestHeader HttpHeaders headers) {
        String url = "http://localhost:8082/books/book-storage/" + endpoint;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    @GetMapping("/books/book-tracker/{endpoint}")
    public ResponseEntity<?> proxyToBookTracker(@PathVariable String endpoint, @RequestHeader HttpHeaders headers) {
        String url = "http://localhost:8081/books/book-tracker/" + endpoint;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }
}

