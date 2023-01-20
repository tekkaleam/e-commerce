package com.shop.ecommerse.domain.entity;

import javax.persistence.*;

import java.util.List;


public class Discount {

    @Id
    @GeneratedValue(generator="optimized-sequence")
    private Long id;

    @OneToMany(mappedBy = "discount")
    private List<Order> orderList;

//    @OneToMany(mappedBy = "discount")
//    private List<Cart> cartList;

    @Column(name = "code")
    private String code;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "status")
    private Integer status;
}
