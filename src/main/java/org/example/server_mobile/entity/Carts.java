package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
@ToString(exclude = "user")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE carts SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItem> cartItems;
    @OneToOne(mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
    Addresses addresses;
    @CreatedDate
    @UpdateTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
    @Column(name = "deleted_at")
    Date deletedAt;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;

    public CartPrice getCartPrice() {
        int totalPrice = 0;
        int discount = 0;

        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            discount += cartItem.getProduct().getDiscount().stream().mapToInt(Discount::getAmount).sum();
        }

        return CartPrice.builder()
                .grandTotal(totalPrice)
                .discount(discount)
                .build();
    }
}
