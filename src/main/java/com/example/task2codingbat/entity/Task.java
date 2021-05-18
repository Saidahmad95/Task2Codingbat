package com.example.task2codingbat.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private boolean completed = false;

    @ManyToOne
    private Topic topic;


    public Task(String name, String description, boolean completed, Topic topic) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.topic = topic;
    }
}
