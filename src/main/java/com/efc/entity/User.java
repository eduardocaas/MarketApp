package com.efc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  @NotBlank(message = "Name field is required")
  private String name;

  private Boolean isEnabled;

  @OneToMany(mappedBy = "user")
  private List<Sale> sales;

}
