package cholog.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldInjection {

    /*
    FieldInjection으로 InjectionBean 주입받기
     */
    // 의존성 주입 : 필드 주입 방식
    // 필드 위에 @Autowired 어노테이션을 붙여줌으로써, 스프링이 해당 빈을 필드에 직접 주입해주는 방식임
    @Autowired
    private InjectionBean injectionBean;

    public String sayHello() {
        return injectionBean.hello();
    }
}
