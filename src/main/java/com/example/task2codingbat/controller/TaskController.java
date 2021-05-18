package com.example.task2codingbat.controller;

import com.example.task2codingbat.entity.Task;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.TaskDTO;
import com.example.task2codingbat.repository.TaskRepository;
import com.example.task2codingbat.service.TaskService;
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
@RequestMapping(value = "/api/task")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTAsks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTask(@PathVariable Integer id) {
        ResponseDTO responseDTO = taskService.getTask(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        ResponseDTO responseDTO = taskService.addTask(taskDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editTask(@PathVariable Integer id, @Valid @RequestBody TaskDTO taskDTO) {
        ResponseDTO responseDTO = taskService.editTask(id, taskDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        ResponseDTO responseDTO = taskService.deleteTask(id);
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
