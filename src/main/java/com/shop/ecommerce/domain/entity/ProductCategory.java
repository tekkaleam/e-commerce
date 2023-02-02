package com.shop.ecommerce.domain.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Builder
public class ProductCategory implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}

