package com.example.faculty.services.interfaces;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.util.paging.Paged;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {

    void createTopic(String name);

    Topic updateTopic(Long topicId, String topicName);

    Topic findTopicById(Long topicId);

    void deleteTopic(Long topicId);

    List<Topic> getAllTopics();

    Paged getTopicsPage(int pageNumber, int size);
}
