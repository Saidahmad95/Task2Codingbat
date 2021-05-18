package com.example.task2codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Email(message = "Email is not valid !")
    @NotNull(message = "Email must not be null !")
    @NotEmpty(message = "Email must not be empty !")
    private String email;

    @NotNull(message = "Password not be empty !")
    private String password;

    @NotNull(message = "Fullname must not be empty !")
    private String fullName;

    @NotNull(message = "User tasks ids must not be empty !")
    private List<Integer> tasksIDs;

    @NotNull(message = "User star-badge id must not be empty !")
    private Integer starBadgeID;
}
