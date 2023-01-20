package com.shop.ecommerse.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Builder
public class Category implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    @Builder.Default
    @JsonManagedReference
    private Set<CategoryList> categoryListSet = Collections.emptySet();

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}

