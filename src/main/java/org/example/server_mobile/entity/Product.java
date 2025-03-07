package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.ShoeSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE product SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    String description;
    Double price;

    @ElementCollection(targetClass = ShoeSize.class)
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    List<ShoeSize> size;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductMedia> productMedia;
    @ManyToMany
    Set<Discount> discount;
    @ManyToMany
    Set<Wishlist> wishlist;
    Byte status;
    Double stock;
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
