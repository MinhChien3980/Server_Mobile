package org.example.server_mobile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.TypeItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "carts_id", nullable = true)
    Carts carts;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    int quantity;
    int grandTotal;
    int discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_item", nullable = false)
    TypeItem typeItem;
}
