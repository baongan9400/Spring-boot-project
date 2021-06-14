package com.ngandang.intern.entity;

import com.ngandang.intern.common.EStatus;
import com.ngandang.intern.model.AuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String location;

    private LocalDateTime schedule;

    private Long total;

    private Double weight;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> details;

    public Order(String location, LocalDateTime schedule, EStatus status, User seller) {
        this.location = location;
        this.schedule = schedule;
        this.status = status;
        this.seller = seller;
    }

    public void setDetails(OrderDetails details) {
        if (this.details.isEmpty()) this.details= new ArrayList<>();
        this.details.add(details);
    }
}


