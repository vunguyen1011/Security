/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1.Service;

import com.example.bai1.DTO.Request.LogoutRequest;
import com.example.bai1.Model.LogoutToken;

import com.example.bai1.Model.User;
import com.example.bai1.Repository.LogoutRepository;
import com.example.bai1.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final LogoutRepository logoutRepository;
    private final UserRepository userRepository;
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${spring.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extarcbyUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(String username) {
             return buildToken(username, jwtExpiration);
    }
    public String generateRefreshToken(String username){
        return buildToken(username, refreshExpiration);
    }
    private String buildToken(String username,long time){
          System.out.println(username);
        // Tìm người dùng trong cơ sở dữ liệu
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.get();

        return Jwts.builder()
                .setSubject(username) // Subject là tên người dùng
                .setIssuer("NguyenVu")
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + time)) // Token hết hạn sau 1 giờ
                .claim("role", user.getRoles()) // Thêm claim "role" với các vai trò của người dùng
                .claim("jwtId", UUID.randomUUID().toString())
                .signWith(getKey()) // Dùng khóa bí mật để ký
                .compact(); 
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void logout(LogoutRequest token) {
        String jwtId = extractClaim(token.getToken(), (t) -> t.get("jwtId", String.class));
        LogoutToken logoutToken = new LogoutToken();
        logoutToken.setToken(jwtId);
        logoutToken.setExpiryTime(new Date());
        logoutRepository.save(logoutToken);
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

    private boolean isLogouted(String token) {
        return logoutRepository.existsById(extractClaim(token, t -> t.get("jwtId", String.class)));
    }

    public boolean isValid(String token, User user) {
        String username = extarcbyUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token) && isChanged(token) && !isLogouted(token);
    }

    public boolean isChanged(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
