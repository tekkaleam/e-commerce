package com.shop.ecommerse.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Order implements Persistable<Long> {

    @Id
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    private Long id;

    // optional = false problem
    /*
    FIW :
     When you make the association mandatory (i.e. optional=false), it trusts you and assumes that an address exists, since the association is mandatory.
     So it directly populates the address field with a proxy, knowing that there is an address referencing the person
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @Column(name = "phone")
    private String phone;
    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "shipped")
    private Integer shipped;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "date")
    @CreationTimestamp
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<OrderDetail> orderDetails;
    @Column(name = "total_price")
    private Double totalPrice;

    public Order(User user, String phone, String shipAddress, LocalDateTime creationDate, List<OrderDetail> orderDetails) {
        this.user = user;
        this.phone = phone;
        this.shipAddress = shipAddress;
        this.creationDate = creationDate;
        this.orderDetails = orderDetails;
        this.totalPrice = getTotalPrice(orderDetails);
    }

    public Double getTotalPrice(List<OrderDetail> orderDetails){
        if (orderDetails.size() == 0) return 0.0;
        return orderDetails.stream()
                .mapToDouble(i -> i.getAmount()*i.getProduct().getPrice())
                .sum();
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
