package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable //내장 타입이래
@Getter
public class Address {
    //값 타임은 변경이 되면 안되는거야!
    //생성할 때만 값이 지정되고, 변경 노노
    private String city;
    private String street;
    private String zipCode;

    //JPA기본스펙은 proxy 등의 기술을 써야할 때가
    //많은데, 이 때 기본생성자가 필요해!
    //이때는 public은 여기저기서 호출하니 protected까지
    protected Address() {
        //함부로 생성하면 안되는것을 나타내주는~
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
