package com.efc.controller;

import com.efc.dto.ResponseDTO;
import com.efc.dto.SaleDTO;
import com.efc.exception.NotFoundException;
import com.efc.exception.SaleException;
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
        return ResponseEntity.ok().body(new ResponseDTO<>("", service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO<>("", service.getById(id)));
        }
        catch (SaleException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody SaleDTO saleDTO) {
        try {
            Long id = service.save(saleDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).body(new ResponseDTO<>("Venda realizada com sucesso", id));
        }
        catch (NotFoundException notFoundException) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(notFoundException.getMessage(), null));
        }
        catch (Exception error) {
            return ResponseEntity.internalServerError().body(new ResponseDTO<>(error.getMessage(), null));
        }
    }

}
