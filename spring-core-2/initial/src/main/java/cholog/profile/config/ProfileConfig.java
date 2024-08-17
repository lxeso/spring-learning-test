package cholog.profile.config;

import cholog.profile.InmemoryMessageRepository;
import cholog.profile.JdbcMessageRepository;
import cholog.profile.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO: Java-based Configuration을 하기 위한 클래스로 지정하기
@Configuration
public class ProfileConfig {

    // TODO: dev 프로파일일 때만 InmemoryMessageRepository 빈이 등록되도록 설정하기
    @Bean
    @Profile("dev")
    public MessageRepository inMemoryMessageRepository() {
        return new InmemoryMessageRepository();
    }

    // TODO: prod 프로파일일 때만 InmemoryMessageRepository 빈이 등록되도록 설정하기
    @Bean
    @Profile("prod")
    public MessageRepository jdbcMessageRepository() {
        return new JdbcMessageRepository();
    }

}
