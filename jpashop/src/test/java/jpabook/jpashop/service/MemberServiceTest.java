package jpabook.jpashop.service;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  // junit 실행할 때 스프링이랑 엮어서 같이 실행하기 위한
@SpringBootTest //  테스트 돌리기 위해서
@Transactional  // 트랜잭션을 걸고 테스트 돌리고나서 rollback (테스트에서만 rollback 하고 기본적으로 service나 repository 같은 곳에서는 rollback 안함 테스트에서만 함)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;


    @Test
//  @Rollback(false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트를 db에 반영 (그리고 실제 트랜잭션은 rollback)  ==> test할 때는 db에 데이터가 남으면 안되기 떄문에 rollback이 되어야 한다.
        Assert.assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);  // 예외가 발새애야 한다. (같은 이름이 들어가기 때문에)

        // then
        fail("예외가 발생해야 한다.");
    }
}