package com.shop.ecommerse.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Customer implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Long id;
    private String customerName;
    @Column(length = 36, columnDefinition = "varchar")
    private UUID apiKey;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
