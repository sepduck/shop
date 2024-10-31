package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariants productVariant;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private Long quantity;
    private boolean sold;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Carts(ProductVariants productVariant, Users user, Long quantity, boolean sold, Status status) {
        this.productVariant = productVariant;
        this.user = user;
        this.quantity = quantity;
        this.sold = sold;
        this.status = status;
    }
}
