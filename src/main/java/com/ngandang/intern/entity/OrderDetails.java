package com.ngandang.intern.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
}


