package com.hans.topics.entity;

public interface TopicWithLikeDislikeCount {
    Topic getTopic();
    Integer getLikeCount();
    Integer getDislikeCount();
}
