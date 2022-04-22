package com.example.demos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
