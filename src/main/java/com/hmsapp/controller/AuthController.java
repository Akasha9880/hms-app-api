package com.hmsapp.controller;

import com.hmsapp.payload.JwtToken;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.payload.UserDto;
import com.hmsapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userdto) {
        userdto.setPassword(BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt(10)));
        UserDto dto = userService.createUser(userdto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto logindto) {
        String token = userService.verifyLogin(logindto);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setType("JWT");

        if (token!=null) {
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("INVALID",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
