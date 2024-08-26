package com.codesofscar.authentication.utils;

import com.codesofscar.authentication.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


@Component
public class JwtUtils {


    private final Supplier<SecretKeySpec> getKey = () -> {
        Key key = Keys.hmacShaKeyFor("116e29db286f6b08e3913426cbd0db6cad0276e9afd85b841ff750e11d7f3a8d983375afe91fd773b79aa1b37610f9763249c8865b2f7d959a6394cbe53f01ca"
                .getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
    };


    private final Supplier<Date> expirationTime = () -> Date
            .from(LocalDateTime.now()
                    .plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant());

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getKey.get())
                .build().parseSignedClaims(token).getPayload();
        return claimResolver.apply(claims);
    }

    public Function<String, String> extractUsername = token ->
            extractClaims(token, Claims::getSubject);
    private final Function<String, Date> extractExpirationDate = token ->
            extractClaims(token, Claims::getExpiration);


    public Function<String, Boolean> isTokenExpired = token -> extractExpirationDate.apply(token)
            .after(new Date(System.currentTimeMillis()));


    public BiFunction<String, String, Boolean> isTokenValid = (token, username) ->
            isTokenExpired.apply(token) && Objects.equals(extractUsername.apply(token), username);


    public Function<UserDetails, String> createJwt = userDetails -> {
        Users user = (Users) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().name());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        return Jwts.builder()
                .signWith(getKey.get())
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime.get())
                .compact();
    };


}
