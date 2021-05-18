package com.example.task2codingbat.service;

import com.example.task2codingbat.entity.Task;
import com.example.task2codingbat.entity.Topic;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.TaskDTO;
import com.example.task2codingbat.repository.TaskRepository;
import com.example.task2codingbat.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TopicRepository topicRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public ResponseDTO getTask(Integer id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return taskOptional.map(task -> new ResponseDTO("Found !", true, task)).orElseGet(() -> new ResponseDTO("Not found !", false));
    }

    public ResponseDTO addTask(TaskDTO taskDTO) {
        Optional<Topic> topicOptional = topicRepository.findById(taskDTO.getTopicID());
        if (!topicOptional.isPresent())
            return new ResponseDTO("Topic with id:'" + taskDTO.getTopicID() + "' not found !", false);

        Optional<Task> byNameAndTopicId = taskRepository.findByNameAndTopicId(taskDTO.getName(), taskDTO.getTopicID());
        if (byNameAndTopicId.isPresent())
            return new ResponseDTO("Topic: '" + topicOptional.get().getName() + "'  already contains task with name: '" + taskDTO.getName() + "' !", false, byNameAndTopicId.get());

        Task newTask = new Task();
        if (taskDTO.isCompleted()) {
            newTask.setCompleted(true);
        }
        newTask.setName(taskDTO.getName());
        newTask.setDescription(taskDTO.getDescription());
        newTask.setTopic(topicOptional.get());

        Task save = taskRepository.save(newTask);
        return new ResponseDTO("Task with name: '" + taskDTO.getName()
                + "' successfully added to topic '" + topicOptional.get().getName() + "' !", true, save);
    }

    public ResponseDTO editTask(Integer id, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent())
            return new ResponseDTO("Task with id:'" + id + "' not found !", false);

        Optional<Topic> topicOptional = topicRepository.findById(taskDTO.getTopicID());
        if (!topicOptional.isPresent())
            return new ResponseDTO("Topic with id:'" + taskDTO.getTopicID() + "' not found!", false);

        Optional<Task> byNameAndTopicIdAndIdNot = taskRepository.findByNameAndTopicIdAndIdNot(taskDTO.getName(), taskDTO.getTopicID(), id);
        if (byNameAndTopicIdAndIdNot.isPresent())
            return new ResponseDTO("Topic: '" + topicOptional.get().getName() + "' already contains task with name: '" + taskDTO.getName() + "' !", false, byNameAndTopicIdAndIdNot.get());

        Task editTask = taskOptional.get();
        editTask.setName(taskDTO.getName());
        editTask.setDescription(taskDTO.getDescription());
        editTask.setTopic(topicOptional.get());
        editTask.setCompleted(taskDTO.isCompleted());

        Task save = taskRepository.save(editTask);

        return new ResponseDTO("Task with id: '" + id + "' successfully edited !", true, save);
    }

    public ResponseDTO deleteTask(Integer id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return new ResponseDTO("Task with id:'" + id + "' successfully deleted !", true);
        }
        return new ResponseDTO("Task with id:'" + id + "' not found !", false);

    }
}
