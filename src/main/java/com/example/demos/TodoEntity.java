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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "todo")

// 엔티티 클래스는 DB테이블을 자바에서 사용하기 위하여, 각각의 테이블을 대응시켜서 생성하게 되며,
// 엔티티 클래스 그 자체가 테이블을 정의할 수 있어야 한다.
/*
ORM
객체와 테이블을 자동으로 매핑(연결)하는 것을 의미한다.
그러나 클래스와 테이블은 서로가 기존부터 호환가능성을 두고 만들어진 것이 아니기 때문에 불일치가 발생하는데,
이를 ORM을 통해 객체 간의 관계를 바탕으로 SQL문을 자동으로 생성하여 불일치를 해결한다.
따라서 ORM을 이용하면 따로 SQL문을 짤 필요없이 객체를 통해 간접적으로 데이터베이스를 조작할 수 있게 된다.
*/

public class TodoEntity {
    @Id                                             // 기본키 지정
    @GeneratedValue(generator = "system-uuid")      // id를 자동으로 생성
                                                    // system-uuid는 GenericGenerato있는 이름으로
                                                    // 기본제공이 아닌 나만의 커스터마이징 을 사용할때
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String userid;
    private String title;
    private boolean done;
}
