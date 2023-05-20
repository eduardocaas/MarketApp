package com.efc.controller;

import com.efc.dto.SaleDTO;
import com.efc.exception.ProductException;
import com.efc.exception.SaleException;
import com.efc.exception.UserException;
import com.efc.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(service.getById(id));
        } catch (SaleException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity save(@RequestBody SaleDTO saleDTO) {
        try {
            Long id = service.save(saleDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();
        } catch (ProductException | UserException userOrProductError) {
            return ResponseEntity.badRequest().body(userOrProductError.getMessage());
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error.getMessage());
        }
    }

}
