package com.stimulusfake.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stimulusfake.springboot.domain.posts.Posts;
import com.stimulusfake.springboot.domain.posts.PostsRepository;
import com.stimulusfake.springboot.web.dto.PostsSaveRequestDto;
import com.stimulusfake.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    //@WithMockUser(roles = "USER")로 모의 사용자 사용하기 위해 MockMvc 추가
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach // 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성.
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // 테스트를 위해 인증된 모의 사용자를 만들어서 사용. 즉, 이 어노테이션으로 인해 ROLE_USER권한을 가진 사용자가 API.를 요청하는 것과 동일한 효과
    public void Posts_등록된다() throws Exception{
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title)
                                                .content(content).author("author").build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url)  // mvc.perform():생성된 MockMvc를 통해 API를 테스트. 본문(body)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().title("title")
                                        .content("content").author("author").build());
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle)
                                        .content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //ResponseEntity<Long> responseEntity = restTemplate
        //                                .exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }



    //@WebMvcTest은 jpa기능이 작동하지 않기 때문에, 지금처럼 jpa 기능까지 한꺼번에 test할 경우 @SpringBootTest 사용

    //////// MockMvc 사용으로 인해 주석처리////////////
    //when
    //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
    //then
    //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    //assertThat(responseEntity.getBody()).isGreaterThan(0L);

    //when
    //ResponseEntity<Long> responseEntity = restTemplate
    //                                .exchange(url, HttpMethod.PUT, requestEntity, Long.class);
    //then
    //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    //assertThat(responseEntity.getBody()).isGreaterThan(0L);


}
