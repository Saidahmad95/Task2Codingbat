package com.example.task2codingbat.repository;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import com.example.task2codingbat.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    boolean existsByName(String name);

    Optional<Topic> findByNameAndIdNot(String name, Integer id);
}

