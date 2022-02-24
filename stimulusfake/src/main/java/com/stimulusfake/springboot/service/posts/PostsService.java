package com.stimulusfake.springboot.service.posts;

//API를 만들기 위한 3개의 클래스
//        1. request 데이터를 받을 DTO
//        2. API요청을 받을 Controller
//        3. transaction, 도메인 기능 간의 순서를 보장하는 Service

import com.stimulusfake.springboot.domain.posts.Posts;
import com.stimulusfake.springboot.domain.posts.PostsRepository;
import com.stimulusfake.springboot.web.dto.PostsListResponseDto;
import com.stimulusfake.springboot.web.dto.PostsResponseDto;
import com.stimulusfake.springboot.web.dto.PostsSaveRequestDto;
import com.stimulusfake.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // 생성자로 빈객체를 만드는 효과 (생성자를 주입받는 방식) (final로 하는 모든 필드를 인자값으로 하는 생성자를)
@Service
public class PostsService {

    public final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return  new PostsResponseDto(entity);
    }

    // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변화 -> List로 반환하는 메소드
    @Transactional(readOnly = true) // (readOnly = true) : 트랜잭션 범위는 유지하되, 조회기능만 남겨서 조회 속도 높아짐
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    //.map(PostsListResponseDto::new) == .map(posts -> new PostsListResponseDto(posts)) 두개 같은거


}
