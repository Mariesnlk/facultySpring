package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.repository.TopicRepository;
import com.example.faculty.exception.BadRequestException;
import com.example.faculty.services.interfaces.TopicService;
import com.example.faculty.util.paging.Paged;
import com.example.faculty.util.paging.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    TopicRepository topicRepository;


    @Override
    public void createTopic(String name) {
        if (topicRepository.existsTopicByName(name))
            throw new BadRequestException("This topic " + name + " is already exists");

        topicRepository.save(Topic.builder()
                .createdDate(LocalDate.now())
                .name(name)
                .build());
    }

    @Override
    public Topic updateTopic(Long topicId, String topicName) {
        Topic topic = findTopicById(topicId);
        topic.setCreatedDate(LocalDate.now());
        topic.setName(topicName);
        topicRepository.save(topic);
        return topic;
    }


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

    @Override
    public Paged getTopicsPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Topic> postPage = setTopics(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    // TODO: 16.11.2021 can be also found by topic name
    private Page<Topic> setTopics(Pageable pageable) {
        return topicRepository.findAllByOrderByCreatedDateDesc(pageable);
    }


}
