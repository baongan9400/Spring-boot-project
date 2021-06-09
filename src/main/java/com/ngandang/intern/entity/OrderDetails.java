package com.ngandang.intern.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double total;

    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    public OrderDetails(Double total, Double weight, Order order, Scrap scrap) {
        this.total = total;
        this.weight = weight;
        this.order = order;
        this.scrap = scrap;
    }

    public OrderDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Scrap getScrap() {
        return scrap;
    }

    public void setScrap(Scrap scrap) {
        this.scrap = scrap;
    }
}


