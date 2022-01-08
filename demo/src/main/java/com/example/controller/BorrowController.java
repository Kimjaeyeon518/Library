package com.example.controller;

import com.example.domain.Book;
import com.example.domain.CustomUserDetails;
import com.example.domain.Member;
import com.example.service.BookService;
import com.example.service.BorrowService;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("/borrow/{bookId}")
    public void borrow(@PathVariable("bookId") Long bookId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println("BorrowController.borrow");
        System.out.println("customUserDetails = " + customUserDetails);

//        Member member = memberService.findByEmail(customUserDetails.getUsername());
        borrowService.save(bookId, 4l, true);
    }

    @PostMapping("/borrowBack/{bookId}")
    public void borrowBack(@PathVariable("bookId") Long bookId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        System.out.println("BorrowController.borrow");
        System.out.println("customUserDetails = " + customUserDetails);

//        Member member = memberService.findByEmail(customUserDetails.getUsername());
        borrowService.save(bookId, 4l, false);
    }
}
