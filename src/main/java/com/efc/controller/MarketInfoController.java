package com.efc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class MarketInfoController {

    @GetMapping()
    public ResponseEntity get() {
        return ResponseEntity.ok().body("MarketApp - Version: 1.0.0 - Creator: Eduardo");
    }
}
