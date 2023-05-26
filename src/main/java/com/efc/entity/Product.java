package com.efc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 150, nullable = false)
  @NotBlank(message = "Description field is required")
  private String description;

  @Column(length = 20, precision = 20, scale = 2, nullable = false)
  @NotNull(message = "Price field is required")
  private BigDecimal price;

  @Column(nullable = false)
  @NotNull(message = "Quantity field is required")
  @Min(value = 1, message = "Minimum quantity is 1")
  private Integer quantity;

}
