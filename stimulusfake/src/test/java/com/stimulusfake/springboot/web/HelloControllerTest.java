package com.stimulusfake.springboot.web;


import com.stimulusfake.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// excludeFilters : @WebMvcTest가 SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService를 읽을 수가
//                  없어서 에러가 남. 따라서 이런식으로 스캔대상에서 SecurityConfig를 제거.
// RunWith : 테스트를 진행 할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴 - springRunner라는 스프링 실행자
// 스프링 부트 테스트와 Junit 사이에 연결자 역할.
// @WebMvcTest : Web(Spring MVC)에 집중할 수 있는 어노테이션 -> @Controller, @ControllerAdvice 등을 사용할수 있음
// 단, @Service, @Component, @Repository 등은 사용 불가 - 여기서는 컨트롤러만 사용하기에 선언.
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,  // excludeFilters
                classes = SecurityConfig.class)})
public class HelloControllerTest {

    //@Autowired : 스프링이 관리하는 빈(BEAN)을 주입 받음
    @Autowired
    private MockMvc mvc; //웹 API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점.
    // 이클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있음

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
        // MockMvc를 통해 /hello 주소로 HTTP GET 요청
        // 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할수 있음
        // .andExpect(status().isOk()) : mvc.perform의 결과를 검증
        // 우리가 흔히 알고있는 200, 404, 500 등의 상태를 검증 -> 여기선 OK 즉, 200인지 아닌지 검증
        //.andExpect(content().string(hello)) : mvc.perform의 결과를 검증 -> 응답 본문의 내용을 검증
        // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증


        // MockMvc : 서블릿 컨테이너의 구동 없이, 시뮬레이션 된 MVC 환경에 모의 HTTP 서블릿 요청을 전송하는 기능을 제공
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)    // 1 param
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    // 2 jasonPath
                .andExpect(jsonPath("$.amount", is(amount)));
    }

    // 1 param : API 테스트할 때 사용될 요청 파라미터를 설정. 단, 같은 String만 허용.
    //           그래서 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능
    // 2 jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드
    //           여기서는 name과 amount를 검증하니 $.name, $.amount로 검증

}
