package cholog;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class TodoClientWithWebClient1 {
    private final WebClient webClient;

    // WebClient 객체를 주입받아 생성자를 통해 초기화합니다.
    public TodoClientWithWebClient1(WebClient webClient) {
        this.webClient = webClient;
    }

    // 모든 Todo 리스트를 가져오는 메서드
    public List<Todo> getTodos() {
        // WebClient를 사용하여 GET 요청을 보냅니다.
        return webClient.get()
                // /todos 경로에 GET 요청을 보냅니다.
                .uri("/todos")
                // 요청을 전송하고 응답을 Mono 객체로 받습니다.
                .retrieve()
                // 응답 본문을 Todo 배열로 변환하여 Mono 객체로 반환합니다.
                .bodyToMono(Todo[].class)
                // Mono 객체를 블로킹 방식으로 실행하여 최종적으로 Todo 배열을 List로 변환합니다.
                .map(Arrays::asList)
                // List<Todo> 객체로 반환합니다.
                .block();
    }

    // ID를 기반으로 특정 Todo를 가져오는 메서드
    public Todo getTodoById(Long id) {
        return webClient.get()
                // URI에 해당 ID를 동적으로 주입합니다.
                .uri("/todos/{id}", id)
                // 요청을 전송하고 응답을 Mono 객체로 받습니다.
                .retrieve()
                // 응답이 HttpStatus 404 (Not Found)일 경우 예외를 처리합니다.
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    // 404 상태 코드일 경우 Mono 에러를 발생시켜 예외를 처리합니다.
                    return Mono.error(new TodoException.NotFound(id));
                })
                // 응답을 Todo 객체로 변환하여 반환합니다.
                .bodyToMono(Todo.class)
                // Mono 객체를 블로킹 방식으로 실행하여 최종적으로 Todo 객체를 반환합니다.
                .block();
    }
}
