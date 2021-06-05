package com.ngandang.intern.model.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestScrap {
    @NotBlank(message = "Scrap's name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Digits(fraction = 0, integer = 6)
    private Integer price;

    @NotBlank(message = "Category is required")
    private String category;

    public RequestScrap(String name, Integer price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
