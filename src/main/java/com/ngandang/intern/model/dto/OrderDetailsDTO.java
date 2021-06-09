package com.ngandang.intern.model.dto;

import com.ngandang.intern.entity.Scrap;

public class OrderDetailsDTO {
    private Long id;

    private Double total;

    private Double weight;

    private Scrap scrap;

    public OrderDetailsDTO(Long id, Double total, Double weight, Scrap scrap) {
        this.id = id;
        this.total = total;
        this.weight = weight;
        this.scrap = scrap;
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

    public Scrap getScrap() {
        return scrap;
    }

    public void setScrap(Scrap scrap) {
        this.scrap = scrap;
    }
}
