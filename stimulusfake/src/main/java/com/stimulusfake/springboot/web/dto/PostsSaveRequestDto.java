package com.stimulusfake.springboot.web.dto;

/*
    API를 만들기 위한 3개의 클래스
    1. request 데이터를 받을 DTO
    2. API요청을 받을 Controller
    3. transaction, 도메인 기능 간의 순서를 보장하는 Service

    Web layer : 흔히 사용하는 컨트롤러 등의 뷰 템플릿 영역
                (controllers, exception handlers, filters, view templates, etc)
    Service layer : @Service에 사용되는 서비스 영역. 일반적으로 controller와 Dao 중간 영역에서 사용됨
    Repository layer : database와 같이 데이터 저장소에 접근하는 영역
    Dtos : 계층 간에 데이터 교환을 위한 객체들의 영역.
                ex) 뷰 템플릿 엔진에서 사용될 객체나 repository layer에서 결과로 넘겨준 객체 등이 이들을 이야기한다.
    Domain Model : 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화 시킨것.
                ex) 택시 앱에서 배차, 탑승, 요금 등이 모두 도메인이 될 수 있다.
                    @Entity가 사용된 영역 역시 도메인 모델

 */

import com.stimulusfake.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder().title(title).content(content).author(author).build();
    }

    // Entity class와 유사한 Dto 클래스를 만듬. Entity 클래스는 Request/response 클래스로 사용하면 안되기 때문에.
    // Entity 클래스는 DB와 맞닿은 핵심 클래스. 엔티티클래스를 기준으로 테이블이 생성되고 스키마가 변경된다.
    // 화면 변경 기능은 아주 사소한 변경인데 이를 위해 테이블과 연결된 엔티티 클래스를 변경하는 것은 너무 큰 변경.
    // 수많은 서비스클래스나 비지니스로직들이 엔티티 클래스를 기준으로 동작. 엔티티가 변경되면 여러 클래스에 영향을 끼치지만
    // requset/response dto는 view를 위한 클래스라 정말 자주 변경이 필요.
    // view layer와 db layer의 역활 분리를 철저히 하는게 좋음

}
