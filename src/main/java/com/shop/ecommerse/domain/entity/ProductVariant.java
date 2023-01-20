package com.shop.ecommerse.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "product_variant")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductVariant implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "product_variant_id_seq", sequenceName = "product_variant_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private Float price;

    @Column(name = "image")
    private String image;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Column(name = "live")
    private Integer live;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}