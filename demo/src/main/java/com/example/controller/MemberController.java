package com.example.controller;

import com.example.domain.Book;
import com.example.domain.Member;
import com.example.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String findAll(Model model, Pageable pageable, @RequestParam(value = "role", required = false) String role){
        model.addAttribute("pages", memberService.findAll(role, pageable));
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", pageable.getPageNumber());

        return "book/bookList";
    }

    @PostMapping
    public String signup(@ModelAttribute Member member) {
        System.out.println("MemberController.signup");
        System.out.println("member = " + member);
        memberService.save(member);
        return "login";
    }

}
