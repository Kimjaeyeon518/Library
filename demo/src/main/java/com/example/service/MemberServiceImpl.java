package com.example.service;

import com.example.domain.Member;
import com.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }

    @Override
    public Page<Member> findAll(String role, Pageable pageable) {
        if(role == null || "".equals(role))
            return memberRepository.findAll(pageable);
        else
            return memberRepository.findAll(role, pageable);
    }

    @Override
    public Member update(Long id, Member member) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
