package com.example.faculty.services.interfaces;

import com.example.faculty.database.entity.Topic;

import java.util.List;

public interface TopicService {

    void createTopic(String name);

    void updateTopic(Topic topic);

    Topic findTopicById(Long topicId);

    void deleteTopic(Long topicId);

    List<Topic> getAllTopics();
}
