package com.example.task2codingbat.controller;

import com.example.task2codingbat.entity.Topic;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.TopicDTO;
import com.example.task2codingbat.repository.TopicRepository;
import com.example.task2codingbat.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/topic")
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    TopicRepository topicRepository;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topicList = topicService.getAllTopics();
        return ResponseEntity.ok(topicList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTopicById(@PathVariable Integer id) {
        ResponseDTO responseDTO = topicService.getTopicById(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addTopic(@Valid @RequestBody TopicDTO topicDTO) {
        ResponseDTO responseDTO = topicService.addTopic(topicDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTopic(@PathVariable Integer id, @Valid @RequestBody TopicDTO topicDTO) {
        ResponseDTO responseDTO = topicService.editTopic(id, topicDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer id) {
        ResponseDTO responseDTO = topicService.deleteTopic(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).body(responseDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
