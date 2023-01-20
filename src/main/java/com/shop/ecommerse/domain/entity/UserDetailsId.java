package com.shop.ecommerse.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode // https://stackoverflow.com/questions/2020904/when-and-why-jpa-entities-should-implement-the-serializable-interface
public class UserDetailsId implements Serializable {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "authority_id")
    private Long authorityId;

}
