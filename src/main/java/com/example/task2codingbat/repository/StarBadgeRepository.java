package com.example.task2codingbat.repository;

import com.example.task2codingbat.entity.StarBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarBadgeRepository extends JpaRepository<StarBadge, Integer> {
}
