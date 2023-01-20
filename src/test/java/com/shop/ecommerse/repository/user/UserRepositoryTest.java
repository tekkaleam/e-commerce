package com.shop.ecommerse.repository.user;

import com.shop.ecommerse.domain.Order;
import com.shop.ecommerse.domain.User;
import com.shop.ecommerse.repository.User.UserRepositoryImpl;
import com.shop.ecommerse.repository.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.shop.ecommerse.repository.user.UserDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
public class UserRepositoryTest {

    @Autowired
    private UserRepositoryImpl underTest;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void isShouldGetAll() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
       User user1 =  underTest.save(User.builder()
                .password("98776")
                .username("Anastasia")
                .firstName("Anastasia")
                .lastName("Efimova")
                .email("efimova12@gmail.com")
                .phoneNumber("89318883422")
                .build());

        List<User> users = underTest.getAll();

        assertThat(users)
                .isNotEmpty()
                .hasSize(2)
                .contains(user, user1);
    }

    @Test
    void isShouldFindByName() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());

        Optional<User> testUser = underTest.findUserByUsername(ADMIN.getUsername());

        assertThat(testUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(user).usingRecursiveComparison());
    }

    @Test
    void isShouldDeleteUserById() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        User user1 =  underTest.save(User.builder()
                .password("98776")
                .username("Anastasia")
                .firstName("Anastasia")
                .lastName("Efimova")
                .email("efimova12@gmail.com")
                .phoneNumber("89318883422")
                .build());

        int i = underTest.delete(user.getId());

        Optional<User> testAns = underTest.findUserByUsername(user.getUsername());
        List<User> users = underTest.getAll();

        assertThat(testAns)
                .isNotPresent();

        assertThat(i).isOne();

        assertThat(users)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void isShouldFindUserByEmail() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());

        Optional<User> testUser = underTest.getByEmail(user.getEmail());

        assertThat(testUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(user).usingRecursiveComparison());
    }

    @Test
    void isShouldSaveUser() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        User user1 = underTest.save(User.builder()
                .password("98776")
                .username("Anastasia")
                .firstName("Anastasia")
                .lastName("Efimova")
                .email("efimova12@gmail.com")
                .phoneNumber("89318883422")
                .build());

        Optional<User> testUser = underTest.get(user.getId());
        Optional<User> checkId = underTest.findUserByUsername(user1.getUsername());

        assertThat(checkId.get().getId())
                .isEqualTo(user1.getId());

        assertThat(testUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(user).usingRecursiveComparison());
    }

    @Test
    void isShouldNotSaveUserWithNullEmail() {
        User user = User.builder().email(null).phoneNumber("832428934623").build();
        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.shop.ecommerse.domain.User.email; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value : com.shop.ecommerse.domain.User.email")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void isShouldNotSaveUserWithNullPhoneNumber() {
        User user = User.builder()
                .email("soidhf@list.ru")
                .phoneNumber(null).build();

        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining(
                        "not-null property references a null or transient value : com.shop.ecommerse.domain.User.phoneNumber; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value : com.shop.ecommerse.domain.User.phoneNumber")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void isShouldGetUserWithOrders() {
        User user = underTest.save(User.builder()
                .password("12345")
                .username("mikhail")
                .firstName("mikhail")
                .lastName("efimov")
                .email("efim12@gmail.com")
                .phoneNumber("89318883455")
                .build());
        Order ADMIN_ORDER1 =  Order.builder().id(null)
                .phone(ADMIN.getPhoneNumber())
                .user(user)
                .orderItems(Collections.emptyList())
                .shipAddress("foo")
                .build();
        Order ADMIN_ORDER2 = Order
                .builder()
                .id(null)
                .phone(ADMIN.getPhoneNumber())
                .user(user)
                .shipAddress("bar")
                .orderItems(Collections.emptyList())
                .build();

        orderRepository.save(ADMIN_ORDER1);
        orderRepository.save(ADMIN_ORDER2);

        user.setOrders(List.of(ADMIN_ORDER1, ADMIN_ORDER2));

        Optional<User> testUserWithOrders = underTest.getUserWithOrders(user.getId());

        assertThat(testUserWithOrders)
                .isPresent();

        List<Order> orders = testUserWithOrders.get().getOrders();

        assertThat(orders)
                .isNotEmpty()
                .contains(ADMIN_ORDER1, ADMIN_ORDER2);
    }
}
