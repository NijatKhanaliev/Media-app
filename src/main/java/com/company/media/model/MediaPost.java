package com.company.media.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MediaPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne(mappedBy = "mediaPost",cascade = CascadeType.ALL,orphanRemoval = true)
    private MediaBlob mediaBlob;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<User> likedByUsers = new HashSet<>();

    public MediaPost(String name,String description,LocalDateTime createdAt,User user){
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
    }

}
