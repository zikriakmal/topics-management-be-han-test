package com.hans.topics.service;

import com.hans.topics.dto.*;
import com.hans.topics.entity.Topic;
import com.hans.topics.entity.TopicLike;
import com.hans.topics.entity.User;
import com.hans.topics.repository.TopicLikeRepository;
import com.hans.topics.repository.TopicRepository;
import com.hans.topics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicLikeRepository topicLikeRepository;

    @Autowired
    UserRepository userRepository;

    public TopicResponse createTopic(TopicRequest topicRequest, UserResponse userResponse) {
        User user = userRepository.findById(userResponse.getId());
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long topicCount = topicRepository.countByUserIdAndDateToday(userResponse.getId(),startOfDay, endOfDay);

        if(topicCount > 5){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reach limit topics per day (maximum 5 topics)");
        }

        Topic topic = new Topic();
        topic.setUser(user);
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setCreatedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        topic.setUpdatedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Topic topicResult = topicRepository.save(topic);

        return convertToTopicResponse(topicResult);
    }

    public List<TopicResponse> getAllTopic() {
        List<Topic> allUserPost = topicRepository.findAllByOrderByIdDesc();

        return allUserPost.stream()
                .map(this::convertToTopicResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteTopic(Integer topicId, Integer authId) {

        Topic findId = topicRepository.findById(topicId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        if (Objects.equals(findId.getUser().getId(), authId)) {
            topicRepository.deleteById(topicId);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return true;
    }

    public boolean likeTopic(Integer topicId, UserResponse userResponse, TopicLikeRequest topicLikeRequest) {
        Topic findTopic =  topicRepository.findById(topicId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        TopicLike topicLike = topicLikeRepository.findByTopicIdAndUserId(topicId,userResponse.getId());
        User user = userRepository.findById(userResponse.getId());

        if(topicLike == null){
            TopicLike topicLikeCreate = new TopicLike();
            topicLikeCreate.setLikeType(topicLikeRequest.getLikeType());
            topicLikeCreate.setUser(user);
            topicLikeCreate.setTopic(findTopic);
            TopicLike topicLikeResult = topicLikeRepository.save(topicLikeCreate);
        }else{
            topicLike.setLikeType(topicLikeRequest.getLikeType());
            TopicLike topicLikeResult = topicLikeRepository.save(topicLike);
        }
//        System.out.println(Sting(topicLikeResult));
        return true;
    }


    public List<TopicResponse> getMyTopics(UserResponse userResponse) {
        List<Topic> allUserTopic = topicRepository.findAllByUserId(userResponse.getId());
        return allUserTopic.stream()
                .map(this::convertToTopicResponse)
                .collect(Collectors.toList());
    }

    private TopicResponse convertToTopicResponse(Topic topic) {
        return TopicResponse.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .createdAt(topic.getCreatedAt())
                .updatedAt(topic.getUpdatedAt())
                .FormattedUpdatedAtDifference(topic.getFormattedUpdatedAtDifference())
                .user(UserResponse.builder()
                        .id(topic.getUser().getId())
                        .name(topic.getUser().getName())
                        .username(topic.getUser().getUsername())
                        .build())
                .build();
    }
}
