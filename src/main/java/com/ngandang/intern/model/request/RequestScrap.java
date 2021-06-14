package com.ngandang.intern.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RequestScrap {

    @NotNull(message = "ID is required")
    private Integer id;

    @NotBlank(message = "Scrap's name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Digits(fraction = 0, integer = 6)
    private Integer price;

    @NotBlank(message = "Category is required")
    private String category;
}
