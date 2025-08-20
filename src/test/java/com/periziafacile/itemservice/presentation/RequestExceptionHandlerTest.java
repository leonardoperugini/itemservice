package com.periziafacile.itemservice.presentation;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class RequestExceptionHandlerTest {

    @Test
    void shouldReturnInternalServerError() {
        // Simulate an exception to test the generic error handler
        Exception ex = new Exception("Errore generico");
        ResponseEntity<Map<String, Object>> response = new RequestExceptionHandler().handleGeneric(ex);

        Map<String, Object> body = response.getBody();
        assertEquals(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("Errore generico", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }
}
