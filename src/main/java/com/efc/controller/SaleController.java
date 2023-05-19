package com.efc.controller;

import com.efc.dto.SaleDTO;
import com.efc.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private SaleService service;

    @Autowired
    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody SaleDTO saleDTO) {
        try {
            Long id = service.save(saleDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

}