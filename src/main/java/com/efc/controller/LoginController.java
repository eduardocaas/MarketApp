package com.efc.controller;

import com.efc.dto.LoginDTO;
import com.efc.dto.ResponseDTO;
import com.efc.security.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping()
    public ResponseEntity login(@Valid @RequestBody LoginDTO login) {
        try {
            userDetailsService.verifyUserCredentials(login);
            // todo: gerar token
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
