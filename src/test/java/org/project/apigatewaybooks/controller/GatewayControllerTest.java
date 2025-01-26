package org.project.apigatewaybooks.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GatewayControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GatewayController gatewayController;


    @Test
    void whenProxyToBookStorage_thenCallRestTemplate() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer valid_token");
        String endpoint = "/all-books";
        String url = "http://localhost:8082/books/book-storage/" + endpoint;

        //when
        when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class))
                .thenReturn(ResponseEntity.ok("Success"));
        //then
        ResponseEntity<String> response = gatewayController.proxyToBookStorage(endpoint, headers);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody());

        verify(restTemplate).exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Test
    void proxyToBookTrackerTest_thenCallRestTemplate() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer valid_token");
        String endpoint = "/free-books";
        String url = "http://localhost:8081/books/book-tracker/" + endpoint;

        //when
        when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class))
                .thenReturn(ResponseEntity.ok("Success"));

        //then
        ResponseEntity<String> response = gatewayController.proxyToBookTracker(endpoint, headers);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success", response.getBody());

        verify(restTemplate).exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Test
    void proxyToBookTrackerTest_thenReturnError() {
        //given
        HttpHeaders headers = new HttpHeaders();
        String endpoint = "/free-books";
        String url = "http://localhost:8081/books/book-tracker/" + endpoint;

        //when
        when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class))
                .thenReturn(ResponseEntity.status(500).build());

        //then
        ResponseEntity<String> response = gatewayController.proxyToBookTracker(endpoint, headers);
        assertEquals(500, response.getStatusCodeValue());

        verify(restTemplate).exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

}
