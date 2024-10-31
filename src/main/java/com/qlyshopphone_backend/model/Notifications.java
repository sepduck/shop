package com.qlyshopphone_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @Enumerated(EnumType.STRING)
//    private NotificationType notificationType;
//
//    @OneToOne
//    @JoinColumn(name = "notified_user_id")
//    private Users notifiedUser;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private Users user;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private Products product;

}
