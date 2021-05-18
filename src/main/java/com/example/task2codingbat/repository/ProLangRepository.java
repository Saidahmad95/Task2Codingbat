package com.example.task2codingbat.repository;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface ProLangRepository extends JpaRepository<ProgrammingLanguage, Integer> {
    Optional<ProgrammingLanguage> findByName(@NotNull(message = "Programming language name must not be empty !") String name);
}
