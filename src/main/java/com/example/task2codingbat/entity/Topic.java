package com.example.task2codingbat.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {
    // Mavzular: String, Array,Logic ...

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private int starNumber;

    @ManyToMany
    private List<ProgrammingLanguage> languageList;

    public Topic(String name, String description, int starNumber, List<ProgrammingLanguage> languageList) {
        this.name = name;
        this.description = description;
        this.starNumber = starNumber;
        this.languageList = languageList;
    }
}
