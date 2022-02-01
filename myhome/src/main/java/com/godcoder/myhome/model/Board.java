package com.godcoder.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30) //이방법이 편하지만 자유도의 제약이 있어서 validator만듬 -> 나는 무슨 버전이 높아서 안먹힘 다운그레이드하면 가능
    private String title;
    private String content;

}
