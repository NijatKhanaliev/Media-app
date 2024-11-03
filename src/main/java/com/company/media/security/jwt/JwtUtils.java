package com.company.media.security.jwt;

import com.company.media.model.Role;
import com.company.media.security.user.UserDetail;
import com.company.media.service.user.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class JwtUtils {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Value("${auth.token.expirationInMils}")
    private int expDate;

    @PostConstruct
    public void init() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
            keyPairGen.initialize(256);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate keys", e);
        }
    }

    public String getUsernameFromToken(String token){
       return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateTokenForUser(Authentication authentication){
       UserDetail userDetail = (UserDetail) authentication.getPrincipal();

      List<String> roles = userDetail.getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority).toList();

      return Jwts.builder()
              .setSubject(userDetail.getEmail())
              .claim("id",userDetail.getId())
              .claim("role",roles)
              .setIssuedAt(new Date())
              .setExpiration(new Date(new Date().getTime()+expDate))
              .signWith(privateKey, SignatureAlgorithm.ES256).compact();
    }

    public String generatePasswordResetToken(String email, Set<Role> roles,Long userId){
        List<String> userRoles = roles.stream()
                .map(Role::getName).toList();

        return Jwts.builder()
                .setSubject(email)
                .claim("id",userId)
                .claim("role",userRoles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expDate))
                .signWith(privateKey,SignatureAlgorithm.ES256)
                .compact();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | IllegalArgumentException | SignatureException | MalformedJwtException |
                 UnsupportedJwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

}
