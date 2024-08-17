package cholog.property.config;

import cholog.property.JwtTokenKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: Java-based Configuration을 하기 위한 클래스로 지정하기
@Configuration
public class AuthConfig {
    // TODO: application.properties의 security.jwt.token.secret-key 값을 활용하여 JwtTokenKeyProvider를 빈으로 등록하기
    @Bean
    public JwtTokenKeyProvider jwtTokenKeyProvider(@Value("${security.jwt.token.secret-key}") String secretKey) {
        return new JwtTokenKeyProvider(secretKey);
    }
}
