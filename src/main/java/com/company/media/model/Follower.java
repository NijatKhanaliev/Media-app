package com.company.media.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Follower(Long followerId,User userId){
        this.followerId = followerId;
        this.user = userId;
    }

}
