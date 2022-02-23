package com.stimulusfake.springboot.domain.posts;
// 도메인 패키지에서는 도메인(게시글, 댓글, 회원, 정산, 결제 등 소프트웨어)에 대한 요구사항 혹은 문제영역을 담을 것이다

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // lombok의 어노테이션 - 코드를 단순화 시켜주지만 필수어노테이션은 아님 6 Getter
@NoArgsConstructor // lombok의 어노테이션 5 NoArgsConstructor
@Entity // 1 Entity - JPA의 어노테이션
public class Posts {
    // 실제 DB의 테이블과 매칭될 클래스이며, 보통 ENTITY클래스라 부름
    
    @Id // 2 Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3 GeneratedValue
    private Long id;
    
    @Column(length = 500, nullable = false) // 4 Column
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    private String author;
    
    @Builder // 7 Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;

    }
    
    /*
    1 Entity : 테이블과 링크될 클래스임을 나타냄
                기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭
                ex) SalesManager.java -> sales_manager table
                entity 클래스에서는 setter를 절대 쓰지않음. 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지
                코드상으로 명확하게 구분할 수 없어 차후 기능 변경시 복잡해지기 때문에
    2 id : 해당 테이블의 PK 필드를 나타냄
    3 GeneratedValue : PK의 생성규칙
                스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 가능
    4 Column : 테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨
                사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
                문자열의 경우 varchar(255)가 기본값인데, 사이즈를 500으로 늘리고싶거나 타입을 TEXT로 변경 하고
                싶은 경우에 사용
    5 NoArgsConstructor : 기본 생성자 자동 추가
    6 Getter : 클래스 내 모든 필드에 getter메서드 자동 생성
    7 Builder : 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
     */
    
    
}
