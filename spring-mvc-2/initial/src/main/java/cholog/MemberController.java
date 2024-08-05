package cholog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1); // AtomicLong은 Java에서 제공하는 멀티스레드 환경에서 사용하기 위해 설계된 클래스. 이 클래스는 long 값에 대해 원자적(atomic) 연산을 제공 -> 여러 스레드가 동시에 같은 값을 수정하더라도 데이터 무결성을 보장

    @PostMapping("/members")
    @ResponseBody
    public ResponseEntity<Member> create(@RequestBody Member member) {
        // TODO: member 정보를 받아서 생성한다.
        Member newMember = Member.toEntity(member, index.getAndIncrement()); // Member 엔티티와 id 값을 보내 새로운 Member 객체를 리턴 #index.getAndIncrement() 함수의 경우 현재 값을 반환하고, 값을 1 증가시킵니다.
        members.add(newMember); // List<Member>에 추가
        return ResponseEntity.created(URI.create("/members/" + newMember.getId())).build(); //
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> read() {
        // TODO: 저장된 모든 member 정보를 반환한다.
        return ResponseEntity.ok(members); // members 리스트에 있는 모든 Member 객체를 반환하려면 리스트 자체를 반환하면 됨
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> update(@RequestBody Member updateMember, @PathVariable Long id) {
        // TODO: member의 수정 정보와 url 상의 id 정보를 받아 member 정보를 수정한다.
        Member member = members.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        member.update(updateMember);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 member를 삭제한다.
        Member member = members.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        members.remove(member);

        return ResponseEntity.noContent().build();
    }
}
