package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // db에서 찾아서 읽는거기 때문에 readOnly = true 가 성능최적화에 좋다
@RequiredArgsConstructor  // final이 있는 필드만 가지고 생성자를 만든다
public class MemberService {

    private final MemberRepository memberRepository;

//    lombok을 통해 생성자 만든다
//    @Autowired   // constructor injection
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    /**
     * 회원 가입
     */
    @Transactional  // 여기서 readOnly = true 넣으면 저장이 안된다. 그렇기 때문에 따로 @Transactional을 사용
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {  // 실제 사용서비스에서는 동시에 가입하게 되면 동시에 호출되면 문제가 생기니까 멀티쓰레드 같은 상황을 고려해서 db에 member.name을 유니크제약조건을 걸어주는 것이 좋다 (안전)
       List<Member> findMembers = memberRepository.findByName(member.getName());
       if (!findMembers.isEmpty()) {
           throw new IllegalStateException("이미 존재하는 회원입니다");
       }
    }

    /**
     *  회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
