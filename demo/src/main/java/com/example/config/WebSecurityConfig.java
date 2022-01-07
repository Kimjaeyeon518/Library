package com.example.config;

import com.example.jwt.JwtAuthenticationFilter;
import com.example.jwt.JwtTokenProvider;
import com.example.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .formLogin().disable()  // JWT filter 적용을 위해 spring security filter 에서 진행하는 로그인 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 X
            .and()
                .authorizeRequests()    // 요청에 대한 사용권한 체크
                .antMatchers("/signIn", "/signUp", "/", "/books", "/members", "/login").permitAll()    // 누구나 접근 가능
                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER", "ADMIN")    // 누구나 접근 가능
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
