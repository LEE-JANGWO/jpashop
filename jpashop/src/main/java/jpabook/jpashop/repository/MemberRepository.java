package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
//    private EntityManager em;

//    @Autowired
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);  // 해당 Member를 반환함 ( 해당 회원 한명 조회)
    }

    public List<Member> findAll() {  // 회원 리스트 조회
        return em.createQuery("select m from Member m", Member.class)  // (jpql,반환타입)
              .getResultList();
    }

    public List<Member> findByName(String name) {   // 이름에 의한 회원 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)  // :name은 파라미터 바인딩
                .setParameter("name", name)  // "name" 이 파라미터가 바인딩 됨
                .getResultList();
    }
}
