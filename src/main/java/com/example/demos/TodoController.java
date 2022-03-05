package com.example.demos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// * 자바 웹 어플리케이션(기존 스프링)의 대부분은 Java Servlet을 기반으로 하며, 서블릿 기반의 서버를 사용하기 위히여
//  1. HttpServlet을 상속받는 서브 클래스를 작성하고 doGet()를 구현 한다.
//   1.1 HttpServletRequest로 넘어오는 요청 패러미터를 분석한다.
//   1.2 비즈니스 로직을 수행한다.
//   1.3 응답을 보낼 HttpServletResponse를 구축한다.
//  2. Servlet Container 가 해당 서브 클래스를 실행후 응답해야 한다.
// * SpringBoot는 위의 작업을 간략화 하여, 반복작업을 방지하고, 어노테이션및 인터페이스를 통해 비즈니스 로직 및 내부기능 집중할 수 있도록한다.

// * 컨트롤러 레이어 - HTTP 요청 및 응답에 대한 매핑을 스프링이 처리하고, 비즈니스 레이어를 호출하기 위한 간단한로직 및
// URI 요청및 응답에 대한 로직 수행. RestController or @Controller 어노테이션을 붙인다.
@Slf4j
@RestController
@RequestMapping(value = "todo")
public class TodoController {

    // 스프링은 IOC(Inversion Of Control) 컨테이너라고 하는, 싱글톤 객체인 Bean을 담고 관리한다.
    // 싱글톤은 개선된 객체지향 프로그래밍 설계를 위한 디자인 패턴으로서, 여러번 객체를 생성하더라도,
    // 최초 생성자를 통해 생생된 객체를 항상 리턴하게 하며, 메모리도 한번만 할당한다.

    // Bean은 Xml 혹은 어노테이션을 통하여 객체를 생성 및 처리한다.
    // @Bean or @Configuration 을 통하여 선언한다.

    // TodoService가 자동주입 되었다. 즉, TodoController는 TodoService에 의존한다.
    // 의존성 주입이란 해당 클래스가 의존하는 다른 클래스를 외부에서 넘겨받아 주입시킨다는 뜻으로서,
    // 주입 방법에는 생성자, setter, 자동주입의 3가지 방법이 있으며, 변경에 따른 안정성 문제로 생성자 방식의 주입이 가장 안전하다.
    // 프로젝트가 커짐에 따라 객체를 생성 및 관리하는데 유지보수 비용이 많이 들어갈 수 있으므로,
    // 이를 외부(스프링)이 관리하게 하고, 적절하게 주입받게 함으로써 프로그래밍이 이루어진다.

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    // create
    // @RequestBody를 통하여, 수신한 JSON 데이터에 따라 dto를 매핑한다.
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try{
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserid(temporaryUserId);
            List<TodoEntity> entities = service.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    // read
    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    // update
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserid(temporaryUserId);
        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    // delete
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserid(temporaryUserId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
