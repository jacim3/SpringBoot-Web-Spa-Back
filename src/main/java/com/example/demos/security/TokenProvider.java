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


// * 인증을 구현하는 방법
// 1. Basic 인증 : HTTP 요청에 아이디와 비밀번호를 같이 보내는 것. : HTTP 요청에다 Authorization 부분에 ID:PW로 이어붙인 후 base64로
// 인코딩 된 문자열을 함께 보냄 : 디코더를 통해 누구나 ID:PW를 찾아낼 수 있으므로 보안 취약. : 로그아웃 구현 불가 및 서비스가 늘어남에 따른
// 인증을 위한 DB 과부하가 기하 급수적으로 증가한다 (스케일 문제).

// 2. 토큰 기반 인증 : 토큰은 사용자를 구별할 수 있는 문자열. 세션과 같이 유저의 정복를 저장하는 대신, 최초 로그인시 사용하는 서버의 종류에 따라
// 서로 다른 형식의 토큰을 만들어 반환하므로, 이를 브라우저의 localStorage에 저장 및 반환하는 방식으로 인증을 구현한다. : ID/PW 대신 토큰을 이용하므로 보안이 안전
// 서버가 마음대로 토큰을 사용하므로, 인가정보의 유효시간을 정해 관리. 그러나 위와 같이 인증에 따른 근본적인 스케일(세션)의 문제를 해결할 수는 없다.

// * 세션 기반 인증 : 유저가 로그인 시, 세션이 서버메모리에 저장. 이 때 세션을 식별하기 위한 세션ID를 기준으로 정보를 저장
// 브라우저에 또한 쿠키로 세션ID가 저장. -> 쿠키에 정보가 담겨있기 때문에, 브라우저는 해당 사이트에 대한 모든 요청에 세션ID를 쿠키에 담아 저장.
// 서버는 클라이언트의 세션ID와 서버 메모리상의 세션ID를 비교하여 인증 수행. : 구현방식이 명확하며, 서버측에서 ID를 관리하므로, 클라이언트의 변조 혹은
// 손상 우려가 없음. : 유저들이 세션 정보를 메모리에 보관하는 부담, 서버에 메모리가 저장됨에 다른 부담, 다중 디바이스에서 로그인할 때, 처리해줘야 할게 있음

// 3.JSON 웹 토큰 : 서버에서 전자 서명된 토큰을 이용함으로써, 인증에 따른 스케일 문제를 해결할 수 있음. 그 방법 중 하나.(JSON Web Token)
// header, payload, signature 로 구성. 위의 토큰인증과 다른점은, 서버가 헤더, payload 외에 인증까지 수행한다는 점.
// ID/PW로 최초 로그인 한 이후, 서버에서는 header와 payload 및 자신의 '시크릿 키'를 통해 인증한 정보를 통하여 전자서명하여 signature까지 반환함으로써
// 이후 인증이 필요할때 header와 payload를 떼서 전자서명한 결과와 signature를 비교하여 인증 수행.
// 인증서버에 부하를 일으키지 않아, 스케일문제 해결.