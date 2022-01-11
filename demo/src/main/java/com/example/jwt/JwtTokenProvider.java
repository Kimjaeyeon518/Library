package com.example.jwt;

import com.example.domain.Member;
import com.example.service.CustomUserDetailsService;
import com.example.service.MemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.header}")
    private String tokenName;

    // 토큰 유효시간 -> 30분
    private final long tokenValidTime = 30 * 60 * 1000l;

    private final CustomUserDetailsService customUserDetailsService;
    private final MemberService memberService;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, String role) {
        Claims claims = Jwts.claims().setSubject(userPk);   // JWT payload에 저장되는 정보단위
        claims.put("role", role); // 정보는 key/value 쌍으로 저장됨
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)  // 정보 저장
                .setIssuedAt(now)   // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime))    // 토큰 만료시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘(HS256)과 signature에 들어갈 secret 값 설정
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Member getMember(String token) {
        return memberService.findByEmail(this.getUserPk(token));
    }
    // 토큰에서 회원정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴. "X-AUTH-TOKEN": "TOKEN값"
    public String resolveTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(tokenName);
    }

    // Request의 Cookie에서 token 값을 가져옴. "X-AUTH-TOKEN"="TOKEN값"
    public String resolveTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(tokenName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    // 토큰의 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        } catch (IllegalArgumentException exception) {
//            log.error("undefined value");
            return false;
        }
    }
}
