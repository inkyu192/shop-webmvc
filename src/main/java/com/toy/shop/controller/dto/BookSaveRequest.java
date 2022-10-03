package com.toy.shop.controller.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class BookSaveRequest {

    @NotEmpty
    private String name;

    @NotNull
    @Min(100)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;
}