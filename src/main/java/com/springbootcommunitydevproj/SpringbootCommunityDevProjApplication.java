package com.springbootcommunitydevproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Jpa로 Entity의 값이 생성되거나 동적으로 변화하면 그 결과를 DB에 자동으로 등록해준다.
@SpringBootApplication
public class SpringbootCommunityDevProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCommunityDevProjApplication.class, args);
    }

}
