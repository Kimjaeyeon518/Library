package com.example.service;

import com.example.domain.CustomUserDetails;
import com.example.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    // 사용자가 로그인할 때 이메일을 입력하면 loadUserByUsername에 인자로 전달한다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // email에 해당하는 정보를 데이터베이스에서 읽어 Member 객체에 저장한다.
        Member member = memberService.findByEmail(email);

        // 해당 이메일에 해당하는 정보가 없으면 UsernameNotFoundException이 발생
        if(member == null)
            throw new UsernameNotFoundException("사용자가 입력한 이메일에 해당하는 사용자를 찾을 수 없습니다.");

        // 정보가 있을 경우엔 UserDetails 인터페이스를 구현한 객체(Member)를 리턴
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(member.getEmail());
        customUserDetails.setPassword(member.getPassword());

        // 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
        // 리스트에 추가한다. UserRole 이름은 "ROLE_"로 시작되야 한다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(member != null) {
            authorities.add(new SimpleGrantedAuthority(member.getRole()));
        }

        // CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
        customUserDetails.setAuthorities(authorities);
        customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);

        return customUserDetails;
    }
}
