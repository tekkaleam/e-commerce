package com.shop.ecommerse.domain.dto;

import com.shop.ecommerse.domain.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Builder
public record CategoryDTO (String name, Type type) {
}
