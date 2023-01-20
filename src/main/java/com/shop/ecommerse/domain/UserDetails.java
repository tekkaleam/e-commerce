package com.shop.ecommerse.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @EmbeddedId
    private UserDetailsId userDetailsId;

    @ManyToOne
    @MapsId("authorityId")
    @JoinColumn(name = "authority_id",
            foreignKey = @ForeignKey(name = "details_authority_fk"))
    private Authority authority;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",
                foreignKey = @ForeignKey(name = "details_user_fk"))
    private User user;

    @Column(name = "created_at", nullable = false, insertable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
