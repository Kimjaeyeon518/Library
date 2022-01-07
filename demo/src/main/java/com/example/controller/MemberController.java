package com.example.controller;

import com.example.domain.Book;
import com.example.domain.Member;
import com.example.jwt.JwtTokenProvider;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원목록 조회
    @GetMapping("/members")
    public String findAll(Model model, Pageable pageable, @RequestParam(value = "role", required = false) String role){
        model.addAttribute("pages", memberService.findAll(role, pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "book/bookList";
    }

    /*
    **  순환 참조 때문에 passwordEncoding, Token 유효성 검사 등의 로직은 서비스단이 아닌 컨트롤러단에서 구현
    */

    // 회원가입
    @PostMapping("/members")
    public String signup(@ModelAttribute Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberService.save(member);
        return "login";
    }

    // JWT 연동 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute Member member, HttpServletResponse response) {
        Member existMember = memberService.findByEmail(member.getEmail());

        if(!passwordEncoder.matches(member.getPassword(), existMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(existMember.getEmail(), existMember.getRole());
        response.setHeader("X-AUTH-TOKEN", token);

        System.out.println("MemberController.login");
        System.out.println("token = " + token);
        System.out.println("existMember.getRole() = " + existMember.getRole());
        System.out.println("SecurityContextHolder.getContext() = " + SecurityContextHolder.getContext().getAuthentication());

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "index";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        SecurityContextHolder.getContext().setAuthentication(null);
        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "login";
    }

}
