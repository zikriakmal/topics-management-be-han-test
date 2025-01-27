package com.hans.topics.repository;

import com.hans.topics.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findAllByOrderByIdDesc();
    List<Topic> findAllByUserId(Integer userId);

}
