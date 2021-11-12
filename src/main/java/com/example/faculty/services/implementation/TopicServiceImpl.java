package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.repository.TopicRepository;
import com.example.faculty.services.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    //only administrator can add topics

    @Autowired
    TopicRepository topicRepository;


    @Override
    public void createTopic(String name) {

    }

    @Override
    public void updateTopic(Topic topic) {

    }

    @Override
    public Topic findTopicById(Long topicId) {
        return null;
    }

    @Override
    public void deleteTopic(Topic topic) {

    }

    @Override
    public List<Topic> getAllTopics() {
        return null;
    }
}
