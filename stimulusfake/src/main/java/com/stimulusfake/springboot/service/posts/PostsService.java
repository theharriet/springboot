package com.stimulusfake.springboot.service.posts;

//API를 만들기 위한 3개의 클래스
//        1. request 데이터를 받을 DTO
//        2. API요청을 받을 Controller
//        3. transaction, 도메인 기능 간의 순서를 보장하는 Service

import com.stimulusfake.springboot.domain.posts.PostsRepository;
import com.stimulusfake.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // 생성자로 빈객체를 만드는 효과 (생성자를 주입받는 방식) (final로 하는 모든 필드를 인자값으로 하는 생성자를)
@Service
public class PostsService {

    public final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
