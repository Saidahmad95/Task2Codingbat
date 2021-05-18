package com.example.task2codingbat.repository;

import com.example.task2codingbat.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByNameAndTopicIdAndIdNot(String name, Integer topic_id, Integer id);
    Optional<Task> findByNameAndTopicId(String name, Integer topic_id);

}
