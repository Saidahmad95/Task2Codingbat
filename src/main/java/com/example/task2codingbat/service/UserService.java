package com.example.task2codingbat.service;

import com.example.task2codingbat.entity.StarBadge;
import com.example.task2codingbat.entity.Task;
import com.example.task2codingbat.entity.User;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.UserDTO;
import com.example.task2codingbat.repository.StarBadgeRepository;
import com.example.task2codingbat.repository.TaskRepository;
import com.example.task2codingbat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    StarBadgeRepository starBadgeRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseDTO getUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> new ResponseDTO("Found !", true, user)).orElseGet(() -> new ResponseDTO("Not found !", false));
    }

    public ResponseDTO addUser(UserDTO userDTO) {
        Optional<User> byEmail = userRepository.findByEmail(userDTO.getEmail());
        if (byEmail.isPresent())
            return new ResponseDTO("User with email: '" + userDTO.getEmail() + "' already exists !", false, byEmail.get());

        Optional<StarBadge> starBadgeOptional = starBadgeRepository.findById(userDTO.getStarBadgeID());
        if (!starBadgeOptional.isPresent())
            return new ResponseDTO("Star badge with id:'" + userDTO.getStarBadgeID() + "' not found !", false);

        List<Task> getTasks = new ArrayList<>();
        List<Integer> tasksIDs = userDTO.getTasksIDs();
        for (Integer integer : tasksIDs) {
            Optional<Task> taskOptional = taskRepository.findById(integer);
            taskOptional.ifPresent(getTasks::add);
            return new ResponseDTO("Task with id:'" + integer + "' not found!", false);
        }

        User newUser = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFullName(), getTasks, starBadgeOptional.get());
        User save = userRepository.save(newUser);
        return new ResponseDTO("User with email: '" + userDTO.getEmail() + "' successfully added !", true, save);
    }

    public ResponseDTO editUser(Integer id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            return new ResponseDTO("User with id:'" + id + "' not found !", false);

        Optional<User> byEmailAndIdNot = userRepository.findByEmailAndIdNot(userDTO.getEmail(), id);
        if (byEmailAndIdNot.isPresent())
            return new ResponseDTO("User with email:' " + userDTO.getEmail() + "' already exists !", false, byEmailAndIdNot.get());

        Optional<StarBadge> starBadgeOptional = starBadgeRepository.findById(userDTO.getStarBadgeID());
        if (!starBadgeOptional.isPresent())
            return new ResponseDTO("Star badge with id:'" + userDTO.getStarBadgeID() + "' not found !", false);

        List<Task> getTasks = new ArrayList<>();
        List<Integer> tasksIDs = userDTO.getTasksIDs();
        for (Integer integer : tasksIDs) {
            Optional<Task> taskOptional = taskRepository.findById(integer);
            taskOptional.ifPresent(getTasks::add);
            return new ResponseDTO("Task with id:'" + integer + "' not found!", false);
        }

        User editUser = userOptional.get();
        editUser.setEmail(userDTO.getEmail());
        editUser.setFullName(userDTO.getFullName());
        editUser.setPassword(userDTO.getPassword());
        editUser.setStarBadge(starBadgeOptional.get());
        editUser.setTaskList(getTasks);

        User save = userRepository.save(editUser);

        return new ResponseDTO("User with id:'" + id + "' successfully edited !", true, save);
    }

    public ResponseDTO deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseDTO("User with id:'" + id + "' successfully deleted !", true);
        }
        return new ResponseDTO("User with id:'" + id + "' not found !", false);
    }
}
