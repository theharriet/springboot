package com.stimulusfake.springboot.config.auth;


import com.stimulusfake.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 1
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // 2
                .and()
                .authorizeRequests() // 3
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/") //6
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    /*
    1 @EnableWebSecurity : SpringSecurity 설정들을 활성화 시켜줌
    2 .csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션을 disable함
    3 .authorizeRequests() : URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers 옵션이 사용가능
    4 antMatchers : 권한 관리 대상을 지정하는 옵션. URL, HTTP 메소드별로 관리가 가능
                   "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 줌
                   "/api/v1/**" 주소를 가진 API는 USER권한을 가진 사람만 가능하도록 함
    5 anyRequest : 설정된 값들 이외 나머지 URL들을 나타냄. 여기서는 authenticated()을 추가하여 나머지 URL들은 모두 인증된(로그인된) 사용자들에게만 허용
    6 logout().logoutSuccessUrl("/") : 로그아웃 기능에 대한 여러 설정의 진입점. 로그아웃 성공시 / 주소로 이동
    7 oauth2Login : OAuth2 로그인 기능에 대한 여러 설정의 진입점
    8 userInfoEndpoint : OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
    9 userService : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                    리소스서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음
     */



}