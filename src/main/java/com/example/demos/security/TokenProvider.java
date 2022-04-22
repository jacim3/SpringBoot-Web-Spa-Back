package com.example.demos.security;

import com.example.demos.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@NoArgsConstructor
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "NMA43DSJKC345VADS";

    public String create(UserEntity entity) {
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(entity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws 를 통하여 token을 base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token 의 서명과 비교
        // 위조되지 않았다면 페이로드를 리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId가 픽ㄹ요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}



