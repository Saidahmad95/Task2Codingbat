package com.example.task2codingbat.payload;

import com.example.task2codingbat.entity.enums.StarBadgeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarBadgeDTO {
    @NotNull(message = "Star badge value must not be empty !")
    private StarBadgeValue value;
    @NotNull(message = "Programming language id must not be empty ")
    private Integer proLangID;
}
