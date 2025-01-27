package com.hans.topics.repository;

import com.hans.topics.entity.TopicLike;
import com.hans.topics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicLikeRepository extends JpaRepository<TopicLike,Integer> {

    TopicLike findByTopicIdAndUserId(Integer topicId, Integer userId);

}
