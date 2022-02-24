package com.stimulusfake.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository<Entity class, PK type> : 인터페이스 생성 후 JpaRepository를 상속하면 기본적이 CRUD 메소드가
//            자동으로 생성된다. 어노테이션 필요없음. 단, Entity 클래스와 기본 entity repository는 함께 위치해야함
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // 전체 조회 화면 만들기
    @Query("Select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();

}
