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
  @NotBlank(message = "Name is required")
  private String name;

  @Column(length = 30, nullable = false)
  @NotBlank(message = "Username is required")
  private String username;

  @Column(length = 30, nullable = false)
  @NotBlank(message = "Password is required")
  private String password;

  private Boolean isEnabled;

  @OneToMany(mappedBy = "user")
  private List<Sale> sales;

}
