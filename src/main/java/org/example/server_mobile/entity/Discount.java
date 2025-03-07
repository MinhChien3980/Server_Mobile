package org.example.server_mobile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String code;
    String description;
    Double discountPercentage;
    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    LocalDateTime validDate;
    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    LocalDateTime invalidDate;
    @CreatedDate
    @UpdateTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
    @UpdateTimestamp
    Date deletedAt;
    int getAmount() {
        return (int) (discountPercentage * 100);
    }
}
