package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //setter없이도 한번 해보기
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;

    @Embedded //내장 타입이구나
    private Address address;

    @OneToMany(mappedBy = "member") //order table에 있는 member 필드에 의해서 매핑된거다~
    private List<Order> orders = new ArrayList<>();

}
