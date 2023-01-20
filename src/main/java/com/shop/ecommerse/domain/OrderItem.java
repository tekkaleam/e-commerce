package com.shop.ecommerse.domain;

import javax.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity(name = "OrderItem")
@Table(name = "order_items")
@ToString(exclude = "order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "order_item_id_seq", sequenceName = "order_item_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "shop_item_id")
    private Product product;
    @Column(name = "amount")
    private Integer amount;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
