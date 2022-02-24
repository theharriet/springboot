package com.stimulusfake.springboot.web;

//API를 만들기 위한 3개의 클래스
//        1. request 데이터를 받을 DTO
//        2. API요청을 받을 Controller
//        3. transaction, 도메인 기능 간의 순서를 보장하는 Service

import com.stimulusfake.springboot.domain.posts.PostsRepository;
import com.stimulusfake.springboot.service.posts.PostsService;
import com.stimulusfake.springboot.web.dto.PostsResponseDto;
import com.stimulusfake.springboot.web.dto.PostsSaveRequestDto;
import com.stimulusfake.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }


}
