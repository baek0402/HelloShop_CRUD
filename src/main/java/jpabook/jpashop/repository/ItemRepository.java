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
        if(item.getId() == null) {
            em.persist(item);
            //최초의 경우에는 DB에 넣고
            //처음에는id값이 없으니까
        } else {
            em.merge(item);
            //재고가 있으면 merge(update개념)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findALL() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
