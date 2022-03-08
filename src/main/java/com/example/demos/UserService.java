package com.example.demos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Slf4j
@Service
// @RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // 생성자 방식 주입하기.
    @Autowired
    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity create(final UserEntity userEntity) {

        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if (repository.existsByEmail(email)) {
            log.warn("Email Already Exists! {}", email);
        }

        // save()를 통하여 저장된 entity 객체 리턴.
        return repository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}
