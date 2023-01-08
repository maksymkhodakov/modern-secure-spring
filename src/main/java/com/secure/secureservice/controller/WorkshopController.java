package com.secure.secureservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workshop-service")
public class WorkshopController {
    @GetMapping
    public ResponseEntity<String> welcomePoint() {
        return ResponseEntity.ok("Welcome point of workshop!");
    }
}
