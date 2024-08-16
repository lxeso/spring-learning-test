package cholog.di;

import org.springframework.stereotype.Service;

@Service
public class ConstructorInjection {
    private InjectionBean injectionBean;

    /*
    ConstructorInjection으로 InjectionBean 주입받기
     */

    // ConstructorInjection 클래스는 InjectionBean이라는 다른 빈을 필요로 함. 아래 sayHello 메서드에서 IngectionBean 객체의 hello 메서드를 쓴 걸 보면 알 수 있음.
    // 의존성 주입 : 생성자 주입 방식 (제일 권장되는 방식) - 객체가 생성될 때, 생성자 메서드가 실행되면서 의존성이 주입됨. 즉, 클래스의 인스턴스가 만들어질 때 의존성이 주입되는 것
    // @Autowired 어노테이션 붙여줘야되지만, 스프링 4.3이후부터 생성자가 1개인 경우, @Autowired 어노테이션 생략 가능
    public ConstructorInjection(InjectionBean injectionBean) {
        this.injectionBean = injectionBean;
    }

    public String sayHello() {
        return injectionBean.hello();
    }
}
