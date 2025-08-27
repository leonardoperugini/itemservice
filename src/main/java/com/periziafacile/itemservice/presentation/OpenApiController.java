package com.periziafacile.itemservice.presentation;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi")
public class OpenApiController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public Resource openapi() {
        return new ClassPathResource("static/v3/openapi.yaml");
    }
}