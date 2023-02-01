package com.shop.ecommerse.domain.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
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

    @JoinColumn(name = "product_category")
    @JsonManagedReference
    @ManyToOne
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductVariant> productVariantList;

    @Column(name = "url")
    private String url;

    @Column(name = "price", nullable = false)
    private Float price;
    @Column(name = "amount")
    @Builder.Default
    private Integer amount = 0;

    @CreationTimestamp
    @Column(updatable = false, insertable = false)
    private Timestamp dateCreated;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
