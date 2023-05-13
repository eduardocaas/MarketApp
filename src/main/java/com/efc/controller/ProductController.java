package com.efc.controller;

import com.efc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private ProductRepository repository;

  public ProductController(@Autowired ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity getAll() {
    return ResponseEntity.ok().body(repository.findAll());
  }

}
