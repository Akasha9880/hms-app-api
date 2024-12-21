package com.hmsapp.controller;

import com.hmsapp.entity.User;
import com.hmsapp.payload.JwtToken;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.payload.ProfileDto;
import com.hmsapp.payload.UserDto;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public UserService userService;
    public UserRepository userRepository;

    public AuthController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // URL used for user Sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userdto) {
        userdto.setPassword(BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt(10)));
        userdto.setRole("ROLE_USER");
        UserDto dto = userService.createUser(userdto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/property/sign-up")
    public ResponseEntity<UserDto> createPropertyOwner(@RequestBody UserDto userdto) {
        userdto.setPassword(BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt(10)));
        userdto.setRole("ROLE_OWNER");
        UserDto dto = userService.createUser(userdto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/blog/sign-up")
    public ResponseEntity<UserDto> createBlogManagerAccount(@RequestBody UserDto userdto) {
        userdto.setPassword(BCrypt.hashpw(userdto.getPassword(), BCrypt.gensalt(10)));
        userdto.setRole("ROLE_BLOGMANAGER");
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

    @GetMapping("/Profile")
    public ResponseEntity<ProfileDto> getUserProfile(
@RequestParam String username
           // @AuthenticationPrincipal 
    ) {
        User user = userRepository.findByUsername(username).get();
        ProfileDto dto = new ProfileDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
