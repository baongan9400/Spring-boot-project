package com.ngandang.intern.model.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class ReqAddOrderDetails {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Scrap ID is required")
    private Integer scrapId;

    @NotNull(message = "weight is required")
    @Digits(fraction = 2, integer = 3)
    private Double weight;

    public ReqAddOrderDetails() {
    }

    public ReqAddOrderDetails(Long orderId, Integer scrapId, Double weight) {
        this.orderId = orderId;
        this.scrapId = scrapId;
        this.weight = weight;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getScrapId() {
        return scrapId;
    }

    public void setScrapId(Integer scrapId) {
        this.scrapId = scrapId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}

