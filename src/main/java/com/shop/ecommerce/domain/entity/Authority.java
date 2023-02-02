package com.shop.ecommerce.domain.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "authority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_id_seq")
    private Long id;

    @Column(name = "role")
    private String role;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "authority"
    )
    private Set<UserDetails> userDetails;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
