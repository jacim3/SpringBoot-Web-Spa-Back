package com.example.demos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")      // id를 자동으로 생성
                                                    // system-uuid는 GenericGenerato있는 이름으로
                                                    // 기본제공이 아닌 나만의 커스터마이징 을 사용할때
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String userid;
    private String title;
    private boolean done;
}
