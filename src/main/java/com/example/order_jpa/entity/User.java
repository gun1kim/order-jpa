package com.example.order_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Column(length = 40, unique = true)
    private String email;

    private String password;

    private String phone;

    private String address;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.BASIC;


}
