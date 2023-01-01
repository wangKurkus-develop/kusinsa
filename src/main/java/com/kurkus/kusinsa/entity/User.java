package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.UserType;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private UserType type;

}
