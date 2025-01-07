/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String Hello(HttpServletRequest req){
        return "Helooo /n"+req.getSession().getId();
    }
    @GetMapping("/api")
    public String Hello1(Authentication authentication){
        return "Xin chao "+authentication.getName();
    }
}
