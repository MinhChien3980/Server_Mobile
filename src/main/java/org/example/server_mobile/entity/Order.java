package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    //    @ManyToOne
//    @JoinColumn(name = "discount_id")
//    Discount discount;
//    @ManyToOne
//    @JoinColumn(name = "carts_id")
//    Carts carts;
    @Column(name = "order_status", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    int status;
    @Column(name = "total_price", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    Double totalPrice = 0.0;
    @Column(name = "total_discount", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    Double totalDiscount = 0.0;
    @Column(name = "grand_total", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")

    Double grandTotal;
    double shippingFee;
    //    @ManyToOne
//    @JoinColumn(name = "addresses_id")
//    Addresses addresses;
    String address;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
    @Column(name = "deleted_at")
    Date deletedAt;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
    @OneToMany(mappedBy = "order")
    List<CartItem> cartItems;

}
