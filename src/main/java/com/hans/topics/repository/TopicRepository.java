package com.hans.topics.repository;

import com.hans.topics.entity.Topic;
import com.hans.topics.entity.TopicWithLikeDislikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findAllByOrderByIdDesc();
    List<Topic> findAllByUserId(Integer userId);

    @Query("SELECT COUNT(t) FROM Topic t WHERE t.user.id = :userId AND t.createdAt BETWEEN :startOfDay AND :endOfDay")
    long countByUserIdAndDateToday(
            @Param("userId") Integer userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT t AS topic, " +
            "SUM(CASE WHEN tl.likeType = 1 THEN 1 ELSE 0 END) AS likeCount, " +
            "SUM(CASE WHEN tl.likeType = 2 THEN 1 ELSE 0 END) AS dislikeCount " +
            "FROM Topic t " +
            "LEFT JOIN t.topicLikes tl " +
            "GROUP BY t " +
            "ORDER BY t.id DESC")
    List<TopicWithLikeDislikeCount> findAllTopicsWithLikeAndDislikeCounts();
}
