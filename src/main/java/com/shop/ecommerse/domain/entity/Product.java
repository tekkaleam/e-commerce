package com.shop.ecommerse.domain.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.Collections;
import java.util.Set;

@Entity(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
@Builder
public class Product implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    @Builder.Default
    private String description = "";

    @JoinColumn(name = "categoty_id")
    @JsonManagedReference
    @ManyToOne
    private ProductCategory categories;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "product",
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<OrderItem> items = Collections.emptySet();

    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "amount")
    @Builder.Default
    private Integer amount = 0;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
