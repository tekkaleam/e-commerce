package com.shop.ecommerse.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Persistable<Long> {

    @Id
    private Long id;
    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}