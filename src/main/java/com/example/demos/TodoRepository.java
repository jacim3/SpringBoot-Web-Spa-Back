package com.example.demos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository는 인터페이스로서, 이 인터페이스를 사용하기 위해서, 새 인터페이스를 생성해 이를 상속해야 한다.
// 기본적인 데이터베이스 오퍼레이션 인터페이스를 제공(save, findById, findAll)
// 일반적으로 인터페이스는 그것을 구현하는 클래스가 있어야 사용이 가능하나, MethodInterceptor라는 AOP 인터페이스를 사용함으로써
// 별도의 작동방식을 가진다.

// JPA는 애플리케이션과 JDBC 사이에서 동작하며, 개발자가 JPA를 사용함에 따라 JPA내부에서 JDBC Api를 사용하여 SQL을 호출하여 DB와
// 통신하게 하므로, 개발자의 노고를 덜어 개발효율을 높여준다.
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // 스프링 JPA가 자동으로 메서드이름을 파싱하여, SELECT * FROM TodoRepository WHERE userId = {userId} 쿼리를 실행
    // 메서드 이름은 '쿼리', 매개변수는 WHERE에 들어갈 필드를 의미
    @Query("select t from TodoEntity t where t.userid = ?1")
    List<TodoEntity> findByUserId(String userId);

/*    @Query("delete from TodoEntity t  where t.userid = ?1")
    List<TodoEntity> deleteByUserId(String userId);*/

}
