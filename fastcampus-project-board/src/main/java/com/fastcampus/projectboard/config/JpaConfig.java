package com.fastcampus.projectboard.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    //auditing 할때 사람 정보를 넣어주기 위한 config을 잡아줄 수있다.
    @Bean
    public AuditorAware<String> auditorAward() {
        return () -> Optional.of("kmkim"); //Todo: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정하자.
    }

}
