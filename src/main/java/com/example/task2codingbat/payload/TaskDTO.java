package com.example.task2codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @NotNull(message = "Task name must not be empty !")
    private String name;

    @NotNull(message = "Task description must not be empty !")
    private String description;

    @NotNull(message = "Completed status must not be empty !")
    private boolean completed;

    @NotNull(message = "Category id must not be empty !")
    private Integer topicID;
}
