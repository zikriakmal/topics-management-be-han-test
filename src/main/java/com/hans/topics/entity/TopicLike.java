package com.hans.topics.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topic_likes")
public class TopicLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "like_type")
    private Integer likeType;

    @ManyToOne
    @JoinColumn(name = "topic_id",nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
