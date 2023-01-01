package com.kurkus.kusinsa.entity;


import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Point extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long score;

    @Column(nullable = false)
    private String division;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
