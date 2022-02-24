package com.stimulusfake.springboot.web;

import com.stimulusfake.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//페이지에 관련된 컨트롤러는 모두 indexController를 사용

@RequiredArgsConstructor
@Controller
public class IndexController {
    // 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다
    // 앞의 경로는 src/main/resources/templates, 뒤의 확장자는 .mustache가 붙는다.
    // 해당 파일은 view resolver가 처리하게 된다. (view resolver는 url요청의 결과를 전달할 타입과 값을 지정하는 관리자)


    private  final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return  "index";
    }
    // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
    //여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달함


    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
