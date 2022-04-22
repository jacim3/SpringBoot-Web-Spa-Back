package com.example.demos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 해당 클래스에 SpringBoot를 설정 및 해당 클래스가 있는 패키지를 베이스 패키지로 설정.
// 스프링의 주요 기능 중 하나가 의존성 주입 컨테이너로서의 기능이며, 이러한 컨테이너 오브젝트인 ApplicationContext에
// 빈을 등록하거나, 찾아서 주입할수 있게 한다.

// @Bean - 메소드 레벨에서 선언하여, 반환되는 인스턴스를 수동으로 Bean으로 등록시킴 (직접적으로 스프링에서 Bean을 관리하기 위함)
// @Component - 클래스 레벨에서 선언하여, @ComponentScan을 통하여 자동으로 Bean을 찾고 등록시킴.
public class DemosApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemosApplication.class, args);
    }
}






