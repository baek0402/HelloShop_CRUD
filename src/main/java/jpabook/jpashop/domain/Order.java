package jpabook.jpashop.domain;

import jpabook.jpashop.domain.constract.DeliveryStatus;
import jpabook.jpashop.domain.constract.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//이걸 생성함으로써, 기본 생성자를 protected로 만들어줌
//아, 직접생성하면 안되고 다른 방법이 있구나 -> createOrder이 있네 이거로 해야지 가 된대
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //fk 이름
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    //cascade
    //order만 persist하면 orderItems도 다같이 persist된대

    //delivery보다 order을 더 자주 조회하니까
    //fk를 order에 두는 선택을 하셨대!
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus status; //주문상태

    //==연관관계 편의 메서드==//
    public void setMember(Member memeber) {
        this.member = memeber;
        member.getOrders().add(this);

        /**
         *  Member member = new Member();
         *  Order order = new Order();
         *  order.setMember(member);
         *  을 위의 두줄로 마무리 가능
         */
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==비즈니스 로직==//
    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //...문법 : 여러개 넘길수있대;;
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소 불가능");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); //주문 여러개 했을 수 있으니 각각에 cancel을 알려주는거다?
        }
    }

    //전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
            //orderItem에있는 totalprice 가져오는이유는 거기에 주문수량이랑 가격이있어서
        }
        return totalPrice;

        //lamda 이용하면..
        /*return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();*/
    }
}
