package com.efc.controller;

import com.efc.entity.Product;
import com.efc.entity.User;
import com.efc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductRepository repository;

  @Autowired
  public ProductController(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity getAll() {
    return ResponseEntity.ok().body(repository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity findById(@PathVariable("id") Long id) {
    Optional<Product> obj = repository.findById(id);
    if (obj.isPresent()) {
      return ResponseEntity.ok().body(obj);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity save(@RequestBody Product product) {
    try {
      Product obj = repository.save(product);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity update(@RequestBody Product product) {
    Optional<Product> obj = repository.findById(product.getId());
    if(obj.isPresent()) {
      repository.save(product);
      return ResponseEntity.ok().body(product);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    try {
      repository.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }


}
