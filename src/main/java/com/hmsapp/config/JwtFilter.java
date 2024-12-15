package com.hmsapp.config;

import com.hmsapp.entity.User;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private UserRepository userRepository;

    //GENERATE CONSTRUCTORS
    public JwtFilter(JWTService jwtService,UserRepository userRepository) {
        this.jwtService = jwtService;

        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8,token.length() -1);
            String username = jwtService.getUsername(jwtToken);

            //TO GET USER OBJECT FROM THE USERNAME
            Optional<User> opUsername = userRepository.findByUsername(username);
            if (opUsername.isPresent()) {
                User user = opUsername.get();
                UsernamePasswordAuthenticationToken
                        userToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, null);
                //TO GET THE URL REQUESTED AND //PROVIDE THE AUTHORIZATION TO THE REQUESTED URL
                userToken.setDetails(new WebAuthenticationDetails(request));
                //SETTING UP THE DETAILS TO THE SPRING SECURITY
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
