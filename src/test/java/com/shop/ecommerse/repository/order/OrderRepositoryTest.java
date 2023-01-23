package com.shop.ecommerse.repository.order;

import com.shop.ecommerse.domain.entity.Order;
import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.repository.User.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class OrderRepositoryTest {
   @Autowired
   private OrderRepositoryImpl underTest;
   @Autowired
   private UserRepositoryImpl userRepository;

    @Test
    void isShouldSaveOrder(){
        User user = User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build();

        userRepository.save(user);

        Order testOrder = underTest.save(Order.builder().id(null)
                .phone("89307772211")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(), user.getId());

        Optional<Order> test = underTest.findOrderByPhoneNumber("89307772211");

        assertThat(test)
             .isPresent()
             .hasValueSatisfying(c -> assertThat(c).isEqualTo(testOrder).usingRecursiveComparison());

    }

    @Test
    void isShouldDeleteOrder() {
        User user = userRepository.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        Order testOrder =  underTest.save(Order.builder().id(null)
               .phone("89307778899")
               .user(user)
               .orderDetails(Collections.emptyList())
               .shipAddress("foo")
               .build(),user.getId());
       Order testOrder1 =  underTest.save(Order.builder().id(null)
               .phone("89307773399")
               .user(user)
               .orderDetails(Collections.emptyList())
               .shipAddress("foo")
               .build(), user.getId());

        List<Order> orderList = underTest.findAllForUserId(user.getId());
        assertThat(orderList)
                .hasSize(2)
                .contains(testOrder, testOrder1);

        int i = underTest.delete(testOrder.getId(), user.getId());

        assertThat(i).isOne();

        assertThat(underTest.findOrderByPhoneNumber("89307778899"))
                .isNotPresent();
    }

    @Test
    void isShouldGetOrderById() {
        User user = userRepository.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        Order testOrder =  underTest.save(Order.builder().id(null)
                .phone("89307778899")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(),user.getId());
        Order testOrder1 =  underTest.save(Order.builder().id(null)
                .phone("89307773399")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(), user.getId());
        Order testOrder2 =  underTest.save(Order.builder().id(null)
                .phone("89307773499")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(), user.getId());

        Optional<Order> test = Optional.ofNullable(underTest.findById(testOrder1.getId(), user.getId()));

        assertThat(test)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(testOrder1).usingRecursiveComparison());
    }

    @Test
    void isShouldGetOrdersByUserId() {
        User user = userRepository.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        Order testOrder =  underTest.save(Order.builder().id(null)
                .phone("89307778899")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(),user.getId());
        Order testOrder1 =  underTest.save(Order.builder().id(null)
                .phone("89307773399")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(), user.getId());
        Order testOrder2 =  underTest.save(Order.builder().id(null)
                .phone("89307773499")
                .user(user)
                .orderDetails(Collections.emptyList())
                .shipAddress("foo")
                .build(), user.getId());

        List<Order> test = underTest.findAllForUserId(user.getId());

        assertThat(test)
                .isNotEmpty()
                .hasSize(3)
                .contains(testOrder, testOrder1, testOrder2);
    }
}
