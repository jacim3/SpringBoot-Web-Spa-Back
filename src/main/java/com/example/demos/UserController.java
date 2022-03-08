package com.example.demos;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demos.security.TokenProvider;

@Slf4j
@Data
@RestController
@RequiredArgsConstructor        // 생성자 주입 방식 간략화 하기
@RequestMapping("/auth")
public class UserController {

    private final UserService service;
    private final TokenProvider tokenProvider;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // 저장할 사용자 객체 생성.
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .userName(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            // repository에 사용자 저장 :
            log.warn("1"+user.getUserName());
            UserEntity registeredUser = service.create(user);
            log.warn("2"+registeredUser.getUserName());

            // 리턴할 사용자 응답 데이터 생성 (비밀번호는 불필요.)
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getEmail())
                    .username(registeredUser.getId())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = service.getByCredentials(userDTO.getEmail(), userDTO.getPassword());

        if (user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user);
            // 저장객체 생성
            final UserDTO responsesUserDTO = UserDTO.builder()
                    .email(user.getUserName())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responsesUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login Failed").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
