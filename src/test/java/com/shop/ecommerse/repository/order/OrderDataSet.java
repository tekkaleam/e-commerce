package com.shop.ecommerse.repository.order;

import com.shop.ecommerse.domain.Order;

import java.util.Collections;

import static com.shop.ecommerse.repository.user.UserDataSet.ADMIN;
import static com.shop.ecommerse.repository.user.UserDataSet.USER;

public class OrderDataSet {
    public static final Order ADMIN_ORDER1 =
            Order.builder().id(null)
                    .phone(ADMIN.getPhoneNumber())
                    .user(ADMIN)
                    .orderItems(Collections.emptyList())
                    .shipAddress("foo")
                    .build();
    public static final Order ADMIN_ORDER2 = Order
            .builder()
            .id(null)
            .phone(ADMIN.getPhoneNumber())
            .user(ADMIN)
            .shipAddress("bar")
            .orderItems(Collections.emptyList())
            .build();
    public static final Order ADMIN_ORDER3 = Order.builder()
            .orderItems(Collections.emptyList())
            .phone(ADMIN.getPhoneNumber())
            .user(ADMIN)
            .shipAddress("foo")
            .build();
    public static final Order USER_ORDER1 = Order.builder()
            .id(null)
            .user(null)
            .phone(USER.getPhoneNumber())
            .shipAddress("san-fran")
            .orderItems(Collections.emptyList())
            .build();
    public static final Order USER_ORDER2 = Order.builder()
            .user(null)
            .phone(USER.getPhoneNumber())
            .shipAddress("new-york")
            .orderItems(Collections.emptyList())
            .build();
    public static final Order USER_ORDER3 = Order.builder()
            .user(null)
            .phone(USER.getPhoneNumber())
            .shipAddress("san")
            .orderItems(Collections.emptyList())
            .build();

}
