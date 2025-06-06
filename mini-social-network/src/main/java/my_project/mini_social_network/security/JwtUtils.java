package my_project.mini_social_network.security;

import com.nimbusds.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key secretKey;

    private final long expiresIn;

    public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiresIn}") long expiresIn) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiresIn = expiresIn;
    }

    public String generateToken(CustomUserDetails userDetails) {

       Instant now = Instant.now();
       Instant expiryDate = now.plus(expiresIn, ChronoUnit.MILLIS);

       return Jwts.builder()
               .subject(userDetails.getUsername())
               .issuedAt(Date.from(now))
               .expiration(Date.from(expiryDate))
               .signWith(secretKey)
               .compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parse(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        Claims claims = (Claims) Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parse(token)
                .getPayload();

        return claims.getSubject();
    }


}
