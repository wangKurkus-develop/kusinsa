package com.kurkus.kusinsa.entity;


import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "device")
@DynamicInsert
public class Device extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "device_token")
    private String deviceToken;
}
