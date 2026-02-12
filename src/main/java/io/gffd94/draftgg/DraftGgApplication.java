package io.gffd94.draftgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// created-at, updated_at 자동 생성
@EnableJpaAuditing
@SpringBootApplication
public class DraftGgApplication {

    public static void main(String[] args) {
        SpringApplication.run(DraftGgApplication.class, args);
    }

}
