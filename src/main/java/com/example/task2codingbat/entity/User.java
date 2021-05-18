package com.example.task2codingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true)
    private String email;

    private String password;

    private String fullName;

    @OneToMany
    private List<Task> taskList;

    @ManyToOne
    private StarBadge starBadge;

    public User(String email, String password, String fullName, List<Task> taskList, StarBadge starBadge) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.taskList = taskList;
        this.starBadge = starBadge;
    }
}
