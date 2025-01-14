/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Service;

import com.example.bai1.Model.User;
import com.example.bai1.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;
    private static final String secretKey = "Ewu4H0jzCNxXtoJS1D9YzLZ/zENfOLLdYQIudk+P5BLa66bFbXt2D/to55MViGnF";

    public String extarcbyUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(String username) {
        System.out.println(username);
        // Tìm người dùng trong cơ sở dữ liệu
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.get();

        return Jwts.builder()
                .setSubject(username) // Subject là tên người dùng
                .setIssuer("NguyenVu")
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token hết hạn sau 1 giờ
                //                .claim("role", user.getRoles()) // Thêm claim "role" với các vai trò của người dùng
                .signWith(getKey()) // Dùng khóa bí mật để ký
                .compact();                      // Trả về token dưới dạng chuỗi
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimReslove) {
        Claims claims = extractAllClaims(token);
        return claimReslove.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractDateToken(token).before(new Date());
    }

    private Date extractDateToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isValid(String token, User user) {
        String username = extarcbyUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
}
