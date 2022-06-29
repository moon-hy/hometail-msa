package com.hometail.gatewayservice.util;

import com.hometail.gatewayservice.dto.TokenUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
public class JwtUtils implements InitializingBean {

    @Value("${token.secret-key}")
    private String SECRET_KEY;

    public Claims extractToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getByteSecret())).build()
                .parseClaimsJws(token).getBody();
    }

    private byte[] getByteSecret() {
        return DatatypeConverter.parseBase64Binary(SECRET_KEY);
    }

    public TokenUser decode(String token) {
        Claims claims = extractToken(token);

        return TokenUser.builder()
                .id(Long.parseLong(claims.getSubject()))
                .role(claims.get("role").toString()).build();
    }

    public boolean isValid(String authorization) {
        try {
            if (authorization == null || !authorization.startsWith("Bearer ")){
                throw new JwtException("Invalid Authorization Header");
            }
            extractToken(authorization.substring(7));
            return true;
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
