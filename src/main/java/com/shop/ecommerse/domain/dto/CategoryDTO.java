package com.shop.ecommerse.domain.dto;

import com.shop.ecommerse.domain.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private String name;
    private Type type;
}
