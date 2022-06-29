package com.hometail.gatewayservice.util;

import com.hometail.gatewayservice.dto.TokenUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        return new TokenUser(
                Long.parseLong(extractToken(token).getSubject()),
                extractToken(token).get("role").toString()
        );
    }

    public boolean isValid(String token) {
        try {
            extractToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
