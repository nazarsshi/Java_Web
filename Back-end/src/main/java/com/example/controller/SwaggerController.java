package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    private static final String REDIRECT_SWAGGER_URL = "redirect:/swagger-ui.html";

    @GetMapping(value = {"", "/swagger"})
    public String redirectToSwaggerAPI() {
        return REDIRECT_SWAGGER_URL;
    }

}
