package com.example.task2codingbat.controller;

import com.example.task2codingbat.entity.User;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.UserDTO;
import com.example.task2codingbat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        ResponseDTO responseDTO = userService.getUser(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping()
    public HttpEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = userService.addUser(userDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = userService.editUser(id, userDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        ResponseDTO responseDTO = userService.deleteUser(id);
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
