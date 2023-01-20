package com.shop.ecommerse.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
@Builder
public class CategoryList {

    @EmbeddedId
    private CategoryListId categoryListId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "list_product_id"))
    @JsonManagedReference
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "list_category_id"))
    @JsonManagedReference
    private Category category;

    @CreationTimestamp
    @Column(name = "ceration_date", nullable = false, insertable = false)
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
}
