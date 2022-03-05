package com.example.demos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// * 퍼시스턴트 레이어 : DB와 통신며 쿼리를 보내고 해석해 엔티티 오브젝트로 변환해준다.
// 엔티티 클래스와 테이블은 서로가 기존부터 호환가능성을 두고 만들어진 것이 아니기 때문에 불일치가 발생할 수 있음.
// 이를 ORM을 통해 객체 간의 관계를 바탕으로 SQL문을 자동으로 생성하여 불일치를 해결한다.
// 따라서 ORM을 이용하면 따로 SQL문을 짤 필요없이 객체를 통해 간접적으로 데이터베이스를 조작할 수 있게 된다.

// JpaRepository는 인터페이스로서, 이 인터페이스를 사용하기 위해서, 새 인터페이스를 생성해 이를 상속해야 한다.
// 기본적인 데이터베이스 오퍼레이션 인터페이스를 제공(save, findById, findAll)
// 일반적으로 인터페이스는 그것을 구현하는 클래스가 있어야 사용이 가능하나, MethodInterceptor라는 AOP 인터페이스를 사용함으로써
// 별도의 작동방식을 가진다.

// * ORM (Object Relation Mapping) : 일반적으로 DB에서 추출해낸 데이터를 프로그래밍에 사용하기 위해 Entity 객체와 대응시키는 작업을 의미.
// 여러 테이블을 자바 내에서 사용하기 위해 엔티티마다 이러한 과정을 반복해야 하나, JPA는 이러한 상황을 해결해주는 스펙(지침) 문서이다.
// 이러한 스펙을 구현하는 프로그램을 JPA Provider라고 하며 대표적으로 Hibernate가 있다.

// JPA는 애플리케이션과 JDBC 사이에서 동작하며, 개발자가 JPA를 사용함에 따라 JPA내부에서 JDBC Api를 사용하여 SQL을 호출하여 DB와
// 통신하게 하므로, 개발자의 노고를 덜어 개발효율을 높여준다.
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // 스프링 JPA가 자동으로 메서드이름을 파싱하여, SELECT * FROM TodoRepository WHERE userId = {userId} 쿼리를 실행
    // 메서드 이름은 '쿼리', 매개변수는 WHERE에 들어갈 필드를 의미
    @Query("select t from TodoEntity t where t.userid = ?1")
    List<TodoEntity> findByUserId(String userId);

    @Query("select t from TodoEntity t")
    List<TodoEntity> getAll();
/*    @Query("delete from TodoEntity t  where t.userid = ?1")
    List<TodoEntity> deleteByUserId(String userId);*/

}
