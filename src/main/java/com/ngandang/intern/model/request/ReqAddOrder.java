package com.ngandang.intern.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngandang.intern.common.EStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReqAddOrder {
    @NotNull(message = "Seller ID is required")
    private Integer sellerId;

    @NotBlank(message = "Location is required")
    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime schedule;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    public ReqAddOrder() {
    }
    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EStatus getStatus() {
        if (this.status == null) this.status = EStatus.PENDING;
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }
}

