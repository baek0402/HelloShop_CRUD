package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("B") //DB에 저장될때 구분될수있는값
public class Book extends Item{

    private String author;
    private String isbn;
}
