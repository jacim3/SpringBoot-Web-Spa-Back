package com.example.demos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository는 인터페이스로서, 이 인터페이스를 사용하기 위해서, 새 인터페이스를 생성해 이를 상속해야 한다.
//
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

}
