package com.hans.topics.controller;

import com.hans.topics.dto.*;
import com.hans.topics.entity.TopicLike;
import com.hans.topics.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TopicController {

    @Autowired
    TopicService topicService;

    @PostMapping(
            path = "/api/v1/topics",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TopicResponse> createTopic(@RequestBody TopicRequest topicRequest, UserResponse userResponse){
        TopicResponse topicResult =  topicService.createTopic(topicRequest,userResponse);
        return WebResponse.<TopicResponse>builder().data(topicResult).build();
    }

    @DeleteMapping(
            path = "/api/v1/topics/{topicId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Boolean> deletePost(@PathVariable Integer topicId, UserResponse userResponse){
        boolean topicDelete = topicService.deleteTopic(topicId,userResponse.getId());
        return WebResponse.<Boolean>builder().data(topicDelete).build();
    }

    @GetMapping(
            path = "api/v1/topics",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TopicResponse>> getAllPost(UserResponse userResponse){
        List<TopicResponse> allTopicResult = topicService.getAllTopic();
        return WebResponse.<List<TopicResponse>>builder().data(allTopicResult).build();
    }

    @PostMapping(
            path = "/api/v1/topics/{topicId}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Boolean> likeTopic(@RequestBody TopicLikeRequest topicLikeRequest, @PathVariable Integer topicId, UserResponse userResponse){
        boolean topicLike = topicService.likeTopic(topicId,userResponse, topicLikeRequest);
        return WebResponse.<Boolean>builder().data(topicLike).build();
    }
}
