package cholog.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterInjection {
    private InjectionBean injectionBean;

    /*
    Setter Injection으로 InjectionBean 주입받기
     */

    // 의존성 주입 : 세터 주입 방식
    // 객체가 생성된 후, 스프링 컨테이너가 세터 메서드를 호출할 때 의존성이 주입됨. 즉, 세터 메서드가 실행할 때 의존성이 주입됨
    // 세터 메서드의 호출은 스프링 프레임 워크가 자동으로 세터 메서드를 인식하고 호출함.
    // 스프링이 세터 메서드를 인식하고 호출하는 방식은 @Autowired 어노테이션과 세터 메서드의 특성인 메서드의 이름(set으로 시작)과 하나의 매개변수를 받아 필드 값을 설정하는 메서드 패턴을 통해 세터 메서드임을 인식함
    @Autowired
    public void setInjectionBean(InjectionBean injectionBean) {
        this.injectionBean = injectionBean;
    }
    public String sayHello() {
        return injectionBean.hello();
    }
}
