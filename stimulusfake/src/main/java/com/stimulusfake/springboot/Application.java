package com.stimulusfake.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application {
    // @SpringBootApplication로 인해 springboot의자동속성, springbean 읽기와 생성을 자동으로 설정
    // 이 어노테이션이 있는 위치부터 읽기때문에 이 클래스는 항상 프로젝트의 최상단에 있어야함

    // Ttd : 테스트가 주도하는 개발
    // 단위테스트 : ttd의 첫번째 단계인 기능단위의 테스트 코드를 작성하는것 (개발초기에 문제가 발생하는것을 찾기위함)
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // 이거로 인해 내장 WAS(web application server) 서버를 실행. -> 톰캣이 필요없다는 소리
    }

    //application.properties에
    //spring.h2.console.enabled=true 추가 후 main메소드 돌리고
    //http://localhost:8080/h2-console
    //jdbc:h2:mem:testdb jdbc url 변경
    
    // HelloControllerTest test 중에 발생한 'JPA metamodel must not be empty!' 에러는 @EnableJpaAuditing와
    // @SpringBootApplication이 함께 있다보니 HelloControllerTest에 있는 @WebMvcTest에서도 스캔하게되서 생기는 에러
    // 따라서 둘을 분리 시켜야함. 이 클래스에서 @EnableJpaAuditing를 삭제하고 config패키지에 JpaConfig를 생성하여
    // 다시 살려낼거임

}
