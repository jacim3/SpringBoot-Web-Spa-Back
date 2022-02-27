package com.example.demos;

import org.springframework.stereotype.Service;

@Service        // 스테레오 타입 어노테이션.
                // 내부에 @Component 를 가지고 있어서 객체가 자동으로 Bean에 등록되며, 특별한 기능차이는 없다.
public class TodoService {
    public String testService() {
        return "Test Service";
    }
}
