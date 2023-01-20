package com.shop.ecommerse.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable( name = "user_authority",
//                joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "ID")},
//                inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "ID")})
//    private Set<Authority> authorities;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "user"
    )
    @Builder.Default
    private Set<UserDetails> userDetails = Collections.emptySet();

    @Column(name = "registration_date")
    @CreationTimestamp
    @Builder.Default
    private LocalDateTime registrationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("creationDate ASC")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
