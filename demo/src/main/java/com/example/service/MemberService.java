package com.example.service;

import com.example.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    Member findByEmail(String email);   // CustomUserDetailsService 에서 사용할 메서드
    Member save(Member member);
    Member findById(Long id);
    Page<Member> findAll(String role, Pageable pageable);
    Member update(Long id, Member member);
    void delete(Long id);
}
