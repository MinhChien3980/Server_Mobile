package org.example.server_mobile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "carts")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE user SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
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
    String addresses;
    @Column(length = 10)
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    String phoneNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOfBirth;
    @CreatedDate
    @UpdateTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    Date deletedAt;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
