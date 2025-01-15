package org.example.server_mobile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "carts")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fullName;
    String email;
    String password;
    @ManyToMany
    Set<Role> role;
    Byte status;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Carts carts;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Wishlist wishlist;
    @OneToMany(mappedBy = "user")
    List<Order> orders;
    @OneToMany(mappedBy = "user")
    Set<Review> review;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ActivityLog> activityLog;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Addresses addresses;
    String phoneNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;
    @CreatedDate
    @UpdateTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
}
