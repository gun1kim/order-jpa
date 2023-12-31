package com.example.order_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ProductUpdateDto {
    private Long productId;
    @NotNull
    @NotBlank
    private String name;
    @Range(min = 100, max = 1000000)
    private int price;

}
