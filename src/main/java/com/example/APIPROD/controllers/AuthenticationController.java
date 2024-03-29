package com.example.APIPROD.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APIPROD.domain.user.AuthenticationDTO;
import com.example.APIPROD.domain.user.LoginResponseDTO;
import com.example.APIPROD.domain.user.RegisterDTO;
import com.example.APIPROD.domain.user.User;
import com.example.APIPROD.domain.user.UserRepository;
import com.example.APIPROD.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));

    }


    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO data){

        if(this.repository.findByLogin(data.login())!= null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login() , encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
