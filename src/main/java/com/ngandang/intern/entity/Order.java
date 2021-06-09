package com.ngandang.intern.entity;

import com.ngandang.intern.common.EStatus;
import com.ngandang.intern.model.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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

    public Order() {
    }

    public Order(String location, LocalDateTime schedule, EStatus status, User seller) {
        this.location = location;
        this.schedule = schedule;
        this.status = status;
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(OrderDetails details) {
        if (this.details.isEmpty()) this.details= new ArrayList<>();
        this.details.add(details);
    }
}


