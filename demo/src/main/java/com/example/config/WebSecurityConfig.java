package com.example.config;

import com.example.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/images/**","/js/**","/scss/**","/vendor/**");       // 인증을 무시할 경로
    }

    /* WebSecurityConfigurerAdapter가 가지고 있는 void configure(AuthenticationManagerBuilder auth)를 오버라이딩
     * AuthenticationFilter가 아이디/암호를 입력해서 로그인 할 때 처리해주는 필터이고 아이디에 해당하는 정보를
     * 데이터베이스에서 읽어 들일 때 UserDetailsService를 구현하고 있는 객체를 이용한다(Spring Security 개요 참조)
     * UserDetailsService는 인터페이스이고, 해당 인터페이스를 구현하고 있는 Bean을 사용
     * 주입된 CustomUserDetailsService객체를 auth.userDetailsService(customUserDetailsService)로 설정하고 있다.
     * 이렇게 설정된 객체는 아이디/암호를 입력 받아 로그인을 처리하는 AuthenticationFilter에서 사용
     * CustomUserDetailsService는 UserDetailsService를 구현하고 있는 객체여야 한다.
     */
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService)    // 유저 정보는 customUserDetailsService 에서 가져온다
//            .passwordEncoder(encoder());
//    }

    /* 로그인 과정없이 사용할 수 있는 경로 추가("/", "/signIn", "/signUp")
     * "/books/**" 는 GET방식의 요청일 때 로그인도 되어 있어야 하고 "USER"권한도 가지고 있어야 접근할 수 있도록 설정
     * 로그인 폼은 "/signIn"이 경로라는 것을 의미. 해당 경로가 요청 왔을 때 로그인 폼을 보여주는 컨트롤러 메소드를 작성해야 한다.
     * 만약 로그인 처리가 실패하게 되면 "/signIn" 으로 forwarding 된다.
     * 로그인을 성공하게 되면 "/"로 redirect 한다.
     * permitAll()이 붙어 있다는 것은 해당 로그인 폼이 아무나 접근 가능하다는 것을 의미한다.(로그인 페이지를 로그인 후에 접근할 수는 없으므로)
     * "/logout"요청이 오면 세션에서 로그인 정보를 삭제한 후 "/"로 redirect
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {     // 페이지 별 접근 권한 설정
        http
            .csrf().disable()

            .authorizeRequests()
                .antMatchers("/signIn", "/signUp", "/", "/books", "/members", "/login").permitAll()    // 누구나 접근 가능
                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER", "ADMIN")    // 누구나 접근 가능
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .antMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated()  // 나머지는 권한이 있기만 하면 접근 가능
            .and()
                .formLogin()    // 로그인에 대한 설정
                .loginPage("/login")       // 로그인 페이지 url
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/books")     // 로그인 성공 시 redirect 하는 url
            .and()
                .logout()       // 로그아웃에 대한 설정
                .logoutSuccessUrl("/login")     // 로그아웃 성공 시 redirect 하는 url
                .invalidateHttpSession(true);    // 로그아웃 시 저장해둔 세션 날리기
    }
}
