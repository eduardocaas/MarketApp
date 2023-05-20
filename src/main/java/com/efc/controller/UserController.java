package com.efc.controller;

import com.efc.dto.UserDTO;
import com.efc.entity.Product;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.repository.UserRepository;
import com.efc.service.UserService;
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

  private final UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity getById(@PathVariable Long id) {
    UserDTO obj = userService.findById(id);
   try {
      return ResponseEntity.ok().body(obj);
    }
   catch (NotFoundException error) {
     return ResponseEntity.badRequest().body(error.getMessage());
   }
  }

  @GetMapping
  public ResponseEntity getAll() {
    return ResponseEntity.ok().body(this.userService.getAll());
  }

  @PostMapping
  public ResponseEntity save(@RequestBody User user) {
    try {
      user.setIsEnabled(true);
      UserDTO obj = userService.save(user);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).body(obj);
    }
    catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity update(@RequestBody User user) {
    try {
      userService.findById(user.getId());
      userService.save(user);
      return ResponseEntity.ok().body(user);
    }
    catch (NotFoundException error) {
      return ResponseEntity.badRequest().body(error.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id){
    try {
      userService.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    catch (Exception error) {
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

}
