package com.periziafacile.itemservice.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestExceptionHandlerTest {

    @Test
    void handleGeneric_shouldReturnInternalServerError() {
        RequestExceptionHandler handler = new RequestExceptionHandler();
        Exception ex = new Exception("Errore generico");
        ResponseEntity<Map<String, Object>> response = handler.handleGeneric(ex);

    assertEquals(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Errore generico", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
    }
}
