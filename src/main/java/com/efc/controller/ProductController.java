package com.efc.controller;

import com.efc.dto.ProductDTO;
import com.efc.entity.Product;
import com.efc.entity.User;
import com.efc.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
  private ModelMapper mapper;

  @Autowired
  public ProductController(ProductRepository repository) {
    this.repository = repository;
    this.mapper = new ModelMapper();
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
  public ResponseEntity save(@Valid @RequestBody ProductDTO productDTO) {
    try {
      Product obj = repository.save(mapper.map(productDTO, Product.class));
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity update(@Valid @RequestBody ProductDTO productDTO) {
    Optional<Product> obj = repository.findById(productDTO.getId());
    if(obj.isPresent()) {
      repository.save(mapper.map(productDTO, Product.class));
      return ResponseEntity.ok().body(productDTO);
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
