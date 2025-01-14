/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Controller;

import com.example.bai1.DTO.Request.RegisterUser;
import com.example.bai1.DTO.Response.Apiresponse;
import com.example.bai1.Model.User;
import com.example.bai1.Service.AuthenticationService;
import com.example.bai1.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/token/{username}")
    Apiresponse<String> generateToken(@PathVariable String username) {
        String token = jwtService.generateToken(username);
        return Apiresponse.<String>builder()
                .result(token)
                .build();

    }
        private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public Apiresponse<User> signup(@RequestBody RegisterUser registerUser) {
        User user = authenticationService.register(registerUser);
        return Apiresponse.<User>builder()
                .result(user)
                .build();
    }
}
