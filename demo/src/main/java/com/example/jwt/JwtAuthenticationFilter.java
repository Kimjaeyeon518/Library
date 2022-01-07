package com.example.jwt;

import com.example.service.CustomUserDetailsService;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
            String token = jwtTokenProvider.resolveToken(request);  // request Header에서 JWT를 받아옴

            if (token != null && jwtTokenProvider.validateToken(token)) {    // 유효한 토큰인지 확인
                Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
                SecurityContextHolder.getContext().setAuthentication(authentication);   // SecurityContext에 Authentication 객체를 저장
                System.out.println("authentication = " + authentication);
                request.setAttribute("member", jwtTokenProvider.getMember(token));
            } else {
                if (token == null) {
                    System.out.println("401 token null");
                    request.setAttribute("unauthorization", "401 인증키 없음.");
                }

                if (!jwtTokenProvider.validateToken(token)) {
                    System.out.println("401-001 token expired");
                    request.setAttribute("unauthorization", "401-001 인증키 만료.");
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
}
