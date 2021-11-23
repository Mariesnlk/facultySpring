package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    Optional<Topic> findTopicById(Long topicId);

    Boolean existsTopicByName(String topicName);

    List<Topic> findByOrderByCreatedDate();

    Page<Topic> findAllByOrderByCreatedDateDesc(Pageable pageable);

}
