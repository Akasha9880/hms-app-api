package com.hmsapp.service;

import com.hmsapp.entity.User;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.payload.UserDto;
import com.hmsapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper,JWTService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    public UserDto createUser(UserDto userdto) {
        User user = maptoEntity(userdto);
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if (opMobile.isPresent()) {
            throw new RuntimeException("Mobile already exists");
        }
        User savedUser = userRepository.save(user);
        UserDto userDto = mapToDto(savedUser);
        return userDto;
    }

    public String verifyLogin(LoginDto logindto) {
        Optional<User> opuser = userRepository.findByUsername(logindto.getUsername());

        if (opuser.isPresent()) {
            User user = opuser.get();
            if (BCrypt.checkpw(logindto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                return token;
            } else {
                return null;
            }
        }
        return null;
    }
    User maptoEntity(UserDto userdto) {
        User user = modelMapper.map(userdto, User.class);
        return user;
    }
    UserDto mapToDto(User user) {
        UserDto userdto = modelMapper.map(user, UserDto.class);
        return userdto;
    }


}
