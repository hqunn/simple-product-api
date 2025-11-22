package com.lightvision.simple_product_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity // JPA entity
@Table(name = "users")
@Data // Lombok: getters, setters, toString, equals, hashCode ...
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
@Builder // Builder pattern
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Order> orders;
}