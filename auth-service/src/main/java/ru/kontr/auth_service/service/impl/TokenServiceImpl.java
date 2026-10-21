package ru.kontr.auth_service.service.impl;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kontr.auth_service.service.TokenService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    private final String privateKey;

    public TokenServiceImpl(@Value("${registration.jwt.private}") String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    @SneakyThrows
    public void validateToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build();
        try {
            jwtParser.parse(token);
        } catch (Exception e) {
            throw new Exception("Could not verify JWT token integrity!", e);
        }
    }

    @Override
    public String generateToken(String login, String firstName, String lastName, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", firstName);
        claims.put("lastName", lastName);
        claims.put("email", email);
        return generateToken(claims, login);
    }

    private String generateToken(Map<String, Object> extraClaims, String login) {
        return Jwts.builder().claims(extraClaims).subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100_000 * 60 * 24))
                .signWith(getSigningKey()).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(privateKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
