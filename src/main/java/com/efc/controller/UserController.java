package com.efc.controller;

import com.efc.entity.User;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository repository;

  public UserController(@Autowired UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity getById(@PathVariable Long id){
    try {
      return ResponseEntity.ok().body(repository.findById(id));
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity getAll(){
    return ResponseEntity.ok().body(this.repository.findAll());
  }

  @PostMapping
  public ResponseEntity save(@RequestBody User user){
    try {
      user.setIsEnabled(true);
      User obj = repository.save(user);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).build();
    } catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }
}
