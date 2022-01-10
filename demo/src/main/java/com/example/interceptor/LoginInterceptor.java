package com.example.interceptor;

import com.example.domain.CustomUserDetails;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(customUserDetails != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);   // SecurityContext에 Authentication 객체를 저장
//                System.out.println("authentication = " + authentication);
                request.setAttribute("member", memberService.findByEmail(customUserDetails.getUsername()));  // member에 현재 로그인 유저 정보 저장
            }
        } catch (NullPointerException e) {
//            System.out.println("Could not set user authentication in security context => " + e);
        } catch (ClassCastException e) {
//            System.out.println("Bootstrap is not CustomUser => " + e);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
