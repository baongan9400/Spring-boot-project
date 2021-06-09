package com.ngandang.intern.model.dto;

import com.ngandang.intern.common.EStatus;
import com.ngandang.intern.model.AuditModel;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO extends AuditModel {
    private Long id;

    private String location;

    private LocalDateTime schedule;

    private Long total;

    private Double weight;

    private EStatus status;

    private UserBaseDTO buyer;

    private UserBaseDTO seller;

    private List<OrderDetailsDTO> details;

    public OrderDTO() {
    }

    public List<OrderDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailsDTO> details) {
        this.details = details;
    }

    public OrderDTO(Long id, String location, LocalDateTime schedule, Long total, Double weight, EStatus status) {
        this.id = id;
        this.location = location;
        this.schedule = schedule;
        this.total = total;
        this.weight = weight;
        this.status = status;
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

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public UserBaseDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserBaseDTO buyer) {
        this.buyer = buyer;
    }

    public UserBaseDTO getSeller() {
        return seller;
    }

    public void setSeller(UserBaseDTO seller) {
        this.seller = seller;
    }
}


