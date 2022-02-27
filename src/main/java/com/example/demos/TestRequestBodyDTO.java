package com.example.demos;

import lombok.Data;

//-- 매개변수를 받는 방법 3 :
// 객체와 같은 복잡한 데이터 보내기
@Data
public class TestRequestBodyDTO {
    private int id;
    private String message;
}

