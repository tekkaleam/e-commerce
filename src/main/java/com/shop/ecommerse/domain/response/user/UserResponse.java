package com.shop.ecommerse.domain.response.user;

import lombok.Builder;

@Builder
public record UserResponse(String email,
        String firstName,
        String lastName,
        String address,
        String city,
        String phone,
        String country,
        Integer emailVerified) {
}
