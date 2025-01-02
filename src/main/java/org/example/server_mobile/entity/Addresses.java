package org.example.server_mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "addresses")
public class Addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    String addresses;

    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    String city;

    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    String state;

    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    String country;

    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    String zip;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carts_id", nullable = true)
    Carts carts;
}