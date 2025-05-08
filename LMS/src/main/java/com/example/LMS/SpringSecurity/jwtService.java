package com.example.LMS.SpringSecurity;

import com.example.LMS.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class jwtService {
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlbHNlZWR5YmFzbWFsYUBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MTUxODQyNjIsImV4cCI6MTcxNTI2MDY2Mn0.kmWTJlIYayIQIHQyLQ3cjnNHV8pjNcdKkuwhFANbDok".getBytes());

    //private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlbHNlZWR5YmFzbWFsYUBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3MTUxODQyNjIsImV4cCI6MTcxNTI2MDY2Mn0.kmWTJlIYayIQIHQyLQ3cjnNHV8pjNcdKkuwhFANbDok"; // Replace with your secret key


    // Generate JWT Token
    public String generateToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24-hour expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        System.out.println("Generated JWT Token: " + token); // Print the generated token

        return token;
    }


    // Extract username from JWT token
    public String extractUsername(String token) {
        System.out.println("Extracted JWT Token: " + token); // Log the received token

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // Validate the token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        // Cast userDetails to your custom User class
        User user = (User) userDetails;
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
