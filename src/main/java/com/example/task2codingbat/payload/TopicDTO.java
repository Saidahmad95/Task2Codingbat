package com.example.task2codingbat.payload;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    @NotNull(message = "Category name must not be empty !")
    private String name;

    @NotNull(message = "Description must not be empty !")
    private String description;

    @NotNull(message = "Number of stars must not be empty !")
    private int starNumber;

    @NotNull(message = "Programming languages id must not be empty !")
    private List<Integer> proLangIDs;
}
