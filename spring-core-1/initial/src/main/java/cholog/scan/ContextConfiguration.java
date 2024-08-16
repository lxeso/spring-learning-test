package cholog.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
ComponentScan에 대해 학습하고, ComponenetScanBean을 Bean으로 등록하기
 */
@ComponentScan(basePackages = "cholog.scan") // 스프링 애플리케이션에서 어떤 패키지 안에 있는 클래스를 스캔하여 스프링 빈으로 등록할지를 지정하는 어노테이션
public class ContextConfiguration {
}
