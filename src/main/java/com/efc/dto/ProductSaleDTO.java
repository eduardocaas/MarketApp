package com.efc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDTO {

    @NotNull(message = "Product is required")
    private Long product_id;
    @NotNull(message = "Quantity is required")
    private Integer quantity;

}
