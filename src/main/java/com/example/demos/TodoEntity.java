package com.example.demos;


import jdk.nashorn.internal.runtime.regexp.joni.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Builder                // 롬복이 제공하는 클래스로, 객체 생성을 간단하게 하기 위함.
@NoArgsConstructor      // 매개변수가 없는 생성자 구현
@AllArgsConstructor     // 모든 매개변수가 있는 생성자 구현.
@Data                   // 모든 멤버변수 게터세터 구현
@Entity                 // 엔티티 선언
@Table(name = "todo")   // 테이블 선언

// 엔티티 클래스는 DB테이블을 자바에서 사용하기 위하여, 각각의 테이블을 대응시켜서 생성하게 되며,
// 엔티티 클래스 그 자체가 테이블을 정의할 수 있어야 한다.
public class TodoEntity {
    @Id                                             // 기본키 지정
    @GeneratedValue(generator = "system-uuid")      // id를 '자동'으로 생성
                                                    // system-uuid는 GenericGenerato있는 이름으로
                                                    // 기본제공이 아닌 나만의 커스터마이징 을 사용할때
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String userid;
    private String title;
    private boolean done;
}
