package com.example.config;

import com.example.jwt.JwtAuthenticationFilter;
import com.example.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();   // authenticationManager를 Bean 등록
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/images/**","/js/**","/scss/**","/vendor/**");       // 인증을 무시할 경로
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {     // 페이지 별 접근 권한 설정
        http
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .formLogin().disable()  // JWT 연동을 위해 로그인 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X
            .and()
                .authorizeRequests()    // 요청에 대한 사용권한 체크
                .antMatchers("/signIn", "/signUp", "/", "/books", "/members", "/login").permitAll()    // 누구나 접근 가능
                .antMatchers(HttpMethod.POST, "/borrow/**").hasAnyRole("USER", "ADMIN")    // USER, ADMIN만 접근 가능
                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER", "ADMIN")    // USER, ADMIN만 접근 가능
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .antMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated()  // 나머지는 권한이 있기만 하면 접근 가능
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 넣는다.
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

//              jwt 연동 로그인을 위해 주석 처리.
//            .and()
//                .formLogin()    // 로그인에 대한 설정
//                .loginPage("/login")       // 로그인 페이지 url
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/books")     // 로그인 성공 시 redirect 하는 url
//            .and()
//                .logout()       // 로그아웃에 대한 설정
//                .logoutSuccessUrl("/login")     // 로그아웃 성공 시 redirect 하는 url
//                .invalidateHttpSession(true)    // 로그아웃 시 저장해둔 세션 날리기


    }
}
