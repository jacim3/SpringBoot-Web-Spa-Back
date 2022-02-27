package com.example.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
// HTTP 응답을 반환할 때, 비즈니스 로직을 캡슐화 및 추가정보를 반환하기 위하여 데이터 전송 객체(DTO)를 사용한다.

public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    // 엔티티 변환 메서드
    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder().id(dto.getId()).title(dto.getTitle()).done(dto.isDone()).build();
    }
}
