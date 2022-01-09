package com.example.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);  // request Header에서 JWT를 받아옴    -> request Header 에 'X-AUTH-TOKEN'이 안 나오는 상황 (토큰값이 쿠키에 들어가있음)

            if (jwtTokenProvider.validateToken(token)) {    // 유효한 토큰인지 확인
                Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                SecurityContextHolder.getContext().setAuthentication(authentication);   // SecurityContext에 Authentication 객체를 저장
                request.setAttribute("member", jwtTokenProvider.getMember(token));  // member에 현재 로그인 유저 정보 저장
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
}
