package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE product_media SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class ProductMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    String url;
    String mediaType;
    @CreatedDate
    @UpdateTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
    @Column(name = "deleted_at")
    Date deletedAt;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
