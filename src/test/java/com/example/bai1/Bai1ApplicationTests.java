package com.example.bai1;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@RequiredArgsConstructor
class Bai1ApplicationTests {
         @Autowired
    private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
          
            String password="10112004";
            System.out.println(passwordEncoder.encode(password));
            
	}

}
