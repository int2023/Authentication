package com.example.demo.controller;

import com.example.demo.model.MyUser;
import com.example.demo.secure.AuthRequest;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private JWTUtil jwtUtil;
    private AuthenticationManager manager;
    private CustomUserDetailsService service;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public String createAuthenticationToken(@RequestBody AuthRequest request) {
        System.out.println(request);
        Authentication authenticate;
        try {
            authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken
                    (request.getName(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        System.out.println(authenticate);
        return jwtUtil.generateToken((UserDetails) authenticate.getPrincipal());
    }

    @PostMapping ("/reg")
    public MyUser registrate(@RequestBody MyUser user) {
        return service.registration(user);
    }

}
