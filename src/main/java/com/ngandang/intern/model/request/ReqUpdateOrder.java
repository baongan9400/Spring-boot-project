package com.ngandang.intern.model.request;

import com.ngandang.intern.entity.User;

public class ReqUpdateOrder {

    private Long orderId;

    private Integer buyerId;

    public ReqUpdateOrder() { }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBuyer() {
        return buyerId;
    }

    public void setBuyer(Integer buyer) {
        this.buyerId = buyer;
    }
}
