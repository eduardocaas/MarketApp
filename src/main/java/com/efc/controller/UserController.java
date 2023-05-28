package com.efc.controller;

import com.efc.dto.ResponseDTO;
import com.efc.dto.UserDTO;
import com.efc.dto.UserResponseDTO;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }


  @GetMapping(value = "/{id}")
  public ResponseEntity getById(@PathVariable Long id) {
    UserResponseDTO obj = userService.findById(id);
   try {
      return ResponseEntity.ok().body(new ResponseDTO<>("", obj));
    }
   catch (NotFoundException error) {
     return ResponseEntity.badRequest().body(new ResponseDTO<>(error.getMessage(), id));
   }
  }

  @GetMapping
  public ResponseEntity getAll() {
    return ResponseEntity.ok().body(new ResponseDTO<>("",this.userService.getAll()));
  }

  @PostMapping
  public ResponseEntity save(@Valid @RequestBody UserDTO user) {
    try {
      user.setIsEnabled(true);
      UserDTO obj = userService.save(user);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(uri).body(new ResponseDTO<>("User created", obj));
    }
    catch (Exception error) {
      return ResponseEntity.internalServerError().body(new ResponseDTO<>(error.getMessage(), user));
    }
  }

  @PutMapping
  public ResponseEntity update(@Valid @RequestBody UserDTO user) {
    try {
      return ResponseEntity.ok().body(new ResponseDTO<>("User updated", userService.update(user)));
    }
    catch (NotFoundException error) {
      return ResponseEntity.badRequest().body(new ResponseDTO<>(error.getMessage(), user));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id){
    try {
      userService.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    catch (NotFoundException error) {
      return ResponseEntity.badRequest().body(new ResponseDTO<>(error.getMessage(), id));
    }
  }

}
