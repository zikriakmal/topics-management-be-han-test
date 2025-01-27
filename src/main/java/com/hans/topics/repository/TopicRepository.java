package com.hans.topics.repository;

import com.hans.topics.entity.Topic;
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
}
