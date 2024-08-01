package cholog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {


    @GetMapping("/hello")
    public String world(@RequestParam String name, Model model) {
        // TODO: /hello 요청 시 resources/templates/static.html 페이지가 응답할 수 있도록 설정하세요.
        model.addAttribute("name", name);
        // TODO: 쿼리 파라미터로 name 요청이 들어왔을 때 해당 값을 hello.html에서 사용할 수 있도록 하세요.
        return "hello"; // MVC 패턴에선 스프링이 리턴 문자열을 뷰 이름으로 해석하여 "hello" 뷰 템플릿 파일을 찾아 렌더링함
    }

    @GetMapping("/json")
    @ResponseBody // HTTP 바디에 응답 데이터 넣어서 클라이언트에게 반환
    public Person json() {
        // TODO: /json 요청 시 {"name": "brown", "age": 20} 데이터를 응답할 수 있도록 설정하세요.
        return new Person("brown", 20);
    }
}
