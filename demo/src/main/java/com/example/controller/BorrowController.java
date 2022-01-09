package com.example.controller;

import com.example.domain.CustomUserDetails;
import com.example.domain.Member;
import com.example.service.BorrowService;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;
    private final MemberService memberService;

    @PostMapping("/borrow/{bookId}")
    public void borrow(@PathVariable("bookId") Long bookId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = memberService.findByEmail(customUserDetails.getUsername());
        borrowService.save(bookId, member.getId(), true);
    }

    @PostMapping("/borrowBack/{bookId}")
    public void borrowBack(@PathVariable("bookId") Long bookId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = memberService.findByEmail(customUserDetails.getUsername());
        borrowService.save(bookId, member.getId(), false);
    }
}
