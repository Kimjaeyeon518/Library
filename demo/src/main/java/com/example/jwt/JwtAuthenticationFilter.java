package com.example.jwt;

import com.example.domain.CustomUserDetails;
import com.example.domain.Member;
import com.example.service.CustomUserDetailsService;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveTokenFromCookie((HttpServletRequest) request);  // request Cookie에서 JWT 토큰을 받아옴

            if (jwtTokenProvider.validateToken(token)) {    // 유효한 토큰인지 확인
                Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                SecurityContextHolder.getContext().setAuthentication(authentication);   // SecurityContext에 Authentication 객체를 저장

                // request 한번에 jwtTokenProvider.getMember(token)이 너무 많이 수행되어 DB 접근이 3~40번 일어나고 있음 (성능 저하)
                // -> 싱글톤 활용?? 필요해보임
                request.setAttribute("member", jwtTokenProvider.getMember(token));  // member에 현재 로그인 유저 정보 저장
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        chain.doFilter(request, response);
    }
}
