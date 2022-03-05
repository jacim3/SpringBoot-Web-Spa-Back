package com.example.demos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController     // JSON을 리턴하는 웹 서비스임을 명시.
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController() {
        return "Hello World!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello World! testGetMapping";
    }

    //-- 매개변수를 받는 방법 1 : http://localhost:8080/test/123
    // GET방식을 사용하여, URI에 삽입하는 방식으로 간단하게 데이터 보내기
    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World! ID "+ id;
    }

    //-- 매개변수를 받는 방법 2 : http://localhost:8080/test/testRequestParam?id=123
    // 위와 비슷하나, 구분하여 데이터 보내기
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! ID "+ id;
    }

    //-- 매개변수를 받는 방법 3 : http://localhost:8080/test/testRequestBody + {"id":123,"message":"hello"}
    // 객체와 같이 복잡한 데이터 주고받기
    @GetMapping("/testRequestBody")
    public String testControllerRewquestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World ID "+ testRequestBodyDTO.getId() + "Message "+testRequestBodyDTO.getMessage();
    }

    //-- 매개변수를 받는 방법 4 : http://localhost:8080/test/testResponseBody
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm Response DTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).error("noError").build();
        return response;
    }

    //-- 매개변수를 받는 방법 5 :
    // 메서드의 리턴타입을 ResponseEntity로 선언하여,
    // HTTP 응답의 바디뿐 아니라, 여러 다른 매개변수 (status, header)를 조작할 수 있다.
    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(response);
    }
}

