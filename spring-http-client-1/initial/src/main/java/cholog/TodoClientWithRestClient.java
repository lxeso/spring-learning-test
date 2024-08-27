package cholog;

import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TodoClientWithRestClient {
    private final RestClient restClient;

    public TodoClientWithRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Todo> getTodos() {
        // restClient를 사용하여 GET 요청을 보내고, 응답을 Todo[] 배열로 받아옴
        Todo[] todoBody = restClient.get() // HTTP GET 요청을 보냄.
                .uri("/todos") // GET 요청의 URI를 설정.
                .retrieve() // 요청을 보내고, 서버의 응답을 받아옴. 응답을 처리할 준비를 함.
                .body(Todo[].class);  // 응답 바디를 Todo[] 배열로 변환. JSON 응답을 Todo 객체 배열로 매핑함.
        // Todo[] 배열을 List<Todo>로 변환하여 반환
        return Arrays.asList(todoBody); // 배열을 리스트로 변환하여 반환. 이 리스트는 호출한 곳에서 사용 가능.
    }

    public Todo getTodoById(Long id) {
        // TODO: restClient의 get 메서드를 사용하여 요청을 보내고 결과를 Todo로 변환하여 반환
        // TODO: 존재하지 않는 id로 요청을 보낼 경우 TodoException.NotFound 예외를 던짐
        return restClient.get()
                .uri("/todos/{id}", id) // URI를 설정. "{id}" 부분은 매개변수로 받은 id로 대체됨.
                .retrieve()
                .onStatus(status -> status.value() == 404, (req, res) -> { // 상태 코드가 404인 경우 예외를 던짐
                    throw new TodoException.NotFound(id); // 404 상태 코드라면 TodoException.NotFound 예외를 발생시킴.
                })
                .body(Todo.class);
    }
}
