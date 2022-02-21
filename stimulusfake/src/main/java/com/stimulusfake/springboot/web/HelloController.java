package com.stimulusfake.springboot.web;

// 컨트롤러와 관련된 클래스들은 모두 이 패키지(web)에 담겠다

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



// @RestController : 컨트롤러를 JSON을 반환하는 컨트롤러로 만듬
    // 이전에는 @RestController를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌
@RestController
public class HelloController {
    
    // @GetMapping : HTTP Method인 get의 요청을 받을 수 있는 api를 만들어줌
    // 이전에는 @RequestMapping(method=RequestMethod.GET)으로 사용됨
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

}
