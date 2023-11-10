package com.example.order_jpa.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSession {
    private Long userId;
    private String name;
    private String email;
}
