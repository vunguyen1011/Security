/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Controller;

import com.example.bai1.DTO.Request.LoginRequest;
import com.example.bai1.DTO.Request.LogoutRequest;
import com.example.bai1.DTO.Request.RegisterUser;
import com.example.bai1.DTO.Response.Apiresponse;
import com.example.bai1.DTO.Response.AuthenticationResponse;
import com.example.bai1.Model.User;
import com.example.bai1.Service.AuthenticationService;
import com.example.bai1.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/token/{username}")
    Apiresponse<String> generateToken(@PathVariable String username) {
        String token = jwtService.generateToken(username);
        return Apiresponse.<String>builder()
                .result(token)
                .build();

    }

    @PostMapping("/signup")
    public Apiresponse<User> signup(@RequestBody RegisterUser registerUser) {
        User user = authenticationService.register(registerUser);
        return Apiresponse.<User>builder()
                .result(user)
                .build();
    }

    @GetMapping("/signin")
    public Apiresponse<AuthenticationResponse> signin(@RequestBody LoginRequest loginRequets) {
        var jwt = authenticationService.authenticate(loginRequets);
        return Apiresponse.<AuthenticationResponse>builder()
                .result(jwt)
                .build();
    }

    @PostMapping("/logout")
    public Apiresponse<Void> logout(@RequestBody LogoutRequest token) {
        System.out.println(token);
        jwtService.logout(token);
        return Apiresponse.<Void>builder()
                .message("Logout success")
                .build();
    }

    @GetMapping("/refresh-token")
    public Apiresponse<String> refreshToken(@RequestBody AuthenticationResponse authenticationResponse) {
        var acessToken = authenticationService.refreshToken(authenticationResponse);
        return Apiresponse.<String>builder()
                .result(acessToken)
                .build();
    }
}
