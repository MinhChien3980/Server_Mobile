package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
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
    @UpdateTimestamp
    Date deletedAt;

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
