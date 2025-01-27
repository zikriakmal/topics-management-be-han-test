package com.hans.topics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicResponseWithLikeDislike {

    private Integer id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String FormattedUpdatedAtDifference;

    private UserResponse user;

    private Integer like;

    private Integer dislike;

}