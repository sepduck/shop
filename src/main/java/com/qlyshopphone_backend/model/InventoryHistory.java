package com.qlyshopphone_backend.model;

import com.qlyshopphone_backend.model.enums.ChangeReason;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InventoryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    private Long previousQuantity;

    private Long newQuantity;

    @Enumerated(EnumType.STRING)
    private ChangeReason changeReason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public InventoryHistory(Products product, Users user, Long previousQuantity, Long newQuantity, ChangeReason changeReason) {
        this.product = product;
        this.user = user;
        this.previousQuantity = previousQuantity;
        this.newQuantity = newQuantity;
        this.changeReason = changeReason;
    }

}
