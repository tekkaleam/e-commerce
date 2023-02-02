package com.shop.ecommerce.domain.dto;

import com.shop.ecommerce.domain.entity.Type;
import lombok.Builder;


@Builder
public record CategoryDTO (String name, Type type) {
}
