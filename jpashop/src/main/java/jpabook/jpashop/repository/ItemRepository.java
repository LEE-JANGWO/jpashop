package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {  // item은 jpa에 저장하기 전까지는 id값이 없다. 그말은 즉 완전히 새로 생긴 객체.
            em.persist(item);         // 그래서 신규로 등록
        } else {
            em.merge(item);     // item값이 있다. 이건 db에 있는 값을 가지고 와서 저장. 업데이트랑 비슷하다고 보면 됨
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
