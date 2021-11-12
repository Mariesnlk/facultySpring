package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.repository.TopicRepository;
import com.example.faculty.exception.BadRequestException;
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
        if (topicRepository.existsTopicByName(name))
            throw new BadRequestException("This topic " + name + " is already exists");

        topicRepository.save(Topic.builder()
                .name(name)
                .build());
    }

//    @Override
//    public void updateTopic(Topic topicToUpdate) {
//        Topic topic = findTopicById(topicToUpdate.getId());
//        topic.setName(topicToUpdate.getName());
//        topicRepository.save(topic);
//    }

    @Override
    public Topic findTopicById(Long topicId) {
        return topicRepository.findTopicById(topicId).orElseThrow(
                () -> new BadRequestException("Topic with id " + topicId + " does not exist!"));
    }

    @Override
    public void deleteTopic(Long topicId) {
        topicRepository.delete(findTopicById(topicId));
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findByOrderByCreatedDate();
    }
}
