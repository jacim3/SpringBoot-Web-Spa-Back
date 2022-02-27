package com.example.demos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service        // 스테레오 타입 어노테이션.
                // 내부에 @Component 를 가지고 있어서 객체가 자동으로 Bean에 등록되며, 특별한 기능차이는 없다.

// 서비스 레이어는 컨트롤러 및 퍼시스턴스와 분리되어, 비즈니스 로직에 집중한다.
//
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        // 엔티티 생성
        TodoEntity entity = TodoEntity.builder().title("My First Todo Item").done(false).build();
        // 엔티티 저장
        repository.save(entity);
        // 엔티티 검색
        Optional<TodoEntity> savedEntity = repository.findById(entity.getId());
        return savedEntity.map(todoEntity -> todoEntity.getTitle() + " " + todoEntity.getTitle()).orElse("noItem");
    }

    // 생성 메서드
    public List<TodoEntity> create(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
        repository.save(entity);

        log.info("Entity if : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserid());
    }

    private void validate (final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        // 넘겨받은 엔티티의 id를 통하여 엔티티가 유효한지 체크.
        final Optional<TodoEntity> original = repository.findById(entity.getId());


    /*
        if (original.isPresent()) {
            final TodoEntity todo1 = original.get();
            todo1.setTitle(entity.getTitle());
            todo1.setDone(entity.isDone());
        }
    */

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });
        return retrieve(entity.getUserid());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        }catch (Exception e) {
            log.error("error deleting entry", entity.getId(), e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return retrieve(entity.getUserid());
    }

/*    // 조회 메서드
    public List<TodoEntity> read(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }
        return repository.findAll();
    }

    // 수정 메서드
    public List<TodoEntity> update(final TodoEntity entity) {

        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }

        return repository.findByUserId(entity.getUserid());
    }


    // 삭제 메서드
    public List<TodoEntity> delete(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null) {
            log.warn("unknown user");
            throw new RuntimeException("unknown user");
        }

        repository.deleteByUserId(entity.getUserid());
        return repository.findByUserId(entity.getUserid());
    }*/
}
