package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//Component 스캔의 대상이 되어서~
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;
    //Spring이 이 엔티티 매니져 만들어서 그냥 주입해준다


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findALL() {
        //JPQL 사용했음 -> Entity 객체를 대상으로 한 쿼리
        //SQL 은 테이블을 대상으로 하는 쿼리
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.username = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
