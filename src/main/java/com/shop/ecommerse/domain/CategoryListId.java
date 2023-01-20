package com.shop.ecommerse.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class CategoryListId implements Serializable {

    @Column(name = "product_id")
    private Long productId;
    @Column(name = "category_id")
    private Long categoryId;
}
