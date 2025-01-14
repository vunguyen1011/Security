/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Service;

import com.example.bai1.DTO.Request.RegisterUser;
import com.example.bai1.Model.Role;
import com.example.bai1.Model.User;
import com.example.bai1.Repository.RoleRepository;
import com.example.bai1.Repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public User register(RegisterUser registerUser) {
        if (userRepository.existsByUsername(registerUser.getName())) {
            throw new RuntimeException("Tai khoan da ton tai");
        }
        if (userRepository.existsByUsername(registerUser.getEmail())) {
            throw new RuntimeException("Email da duoc dung de dang ky 1 tai khoan khac");
        }
        User newUser = new User();
        newUser.setName(registerUser.getName());
        newUser.setUsername(registerUser.getUsername());
        newUser.setEmail(registerUser.getEmail());
        // Mã hóa mật khẩu
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));

        // Gán quyền cho người dùng (ví dụ quyền USER mặc định)
        Set<Role> roles = Set.of(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role không tồn tại")));
        newUser.setRoles(roles);

        // Lưu người dùng vào cơ sở dữ liệu
        return userRepository.save(newUser);

    }
}
