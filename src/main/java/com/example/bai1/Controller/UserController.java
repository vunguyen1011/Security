/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Controller;

import com.example.bai1.DTO.Request.LoginRequest;
import com.example.bai1.DTO.Request.RegisterUser;
import com.example.bai1.DTO.Response.Apiresponse;
import com.example.bai1.Model.User;
import com.example.bai1.Service.AuthenticationService;
import com.example.bai1.Service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

     @PostMapping("/findUser")
    public Apiresponse<User> findUSer (@RequestBody String username) {
        User user = userService.findUserByUsername(username);
        return Apiresponse.<User>builder()
                .result(user)
                .build();
    }
    @GetMapping()
    public Apiresponse<List<User>> getAll(){
        List<User> users =userService.getAllUsers();
        return Apiresponse.<List<User>>builder()
                .result(users)
                .build();
                
    }
}
