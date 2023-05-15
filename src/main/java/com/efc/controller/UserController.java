package com.efc.controller;

import com.efc.entity.Product;
import com.efc.entity.User;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserRepository repository;

  public UserController(@Autowired UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity getById(@PathVariable Long id) {
    Optional<User> obj = repository.findById(id);
    if (obj.isPresent()) {
      return ResponseEntity.ok().body(obj);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity getAll() {
    return ResponseEntity.ok().body(this.repository.findAll());
  }

  @PostMapping
  public ResponseEntity save(@RequestBody User user) {
    try {
      user.setIsEnabled(true);
      User obj = repository.save(user);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity update(@RequestBody User user) {
    Optional<User> obj = repository.findById(user.getId());
    if(obj.isPresent()){
      repository.save(user);
      return ResponseEntity.ok().body(user);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id){
    try {
      repository.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

}
