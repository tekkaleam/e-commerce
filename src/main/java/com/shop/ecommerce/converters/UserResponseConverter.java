package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.entity.User;
import com.shop.ecommerce.domain.response.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseConverter implements Function<User, UserResponse> {

  public UserResponse apply(User user) {
      return UserResponse
              .builder()
              .address(user.getAddress())
              .phone(user.getPhoneNumber())
              .city(user.getCity())
              .email(user.getEmail())
              .emailVerified(user.getEmailVerified())
              .country(user.getCountry())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .build();

  }

 }
