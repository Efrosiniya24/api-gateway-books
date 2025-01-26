package org.project.apigatewaybooks.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

//    @BeforeEach
//    void setUp() throws Exception {
//        when(response.getWriter()).thenReturn(writer);
//    }

    @Test
    void authHeaderIsNullTest() throws Exception {
        //given
        //when
        when(request.getHeader("Authorization")).thenReturn(null);
        when(response.getWriter()).thenReturn(writer);

        //then
        Boolean result = jwtAuthenticationFilter.preHandle(request, response, null);
        assertFalse(result);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Missing or invalid Authorization header");
    }

    @Test
    void notBearerTokenTest() throws Exception {
        //given
        //when
        when(request.getHeader("Authorization")).thenReturn("IsNotBearerToken");
        when(response.getWriter()).thenReturn(writer);

        //then
        Boolean result = jwtAuthenticationFilter.preHandle(request, response, null);
        assertFalse(result);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Missing or invalid Authorization header");
    }

    @Test
    void tokenIsValidTest() throws Exception {
        //given
        String token = "token";
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Success");

        //when
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(restTemplate.exchange(
                eq("http://auth-service/auth/validate"),
                eq(HttpMethod.GET),
                any(),
                eq(String.class)
        )).thenReturn(mockResponse);

        //then
        Boolean result = jwtAuthenticationFilter.preHandle(request, response, null);
        assertTrue(result);

        verify(response, never()).setStatus(anyInt());
    }
}
