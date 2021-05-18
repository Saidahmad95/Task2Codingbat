package com.example.task2codingbat.controller;

import com.example.task2codingbat.entity.StarBadge;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.StarBadgeDTO;
import com.example.task2codingbat.repository.StarBadgeRepository;
import com.example.task2codingbat.service.StarBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/starBadge")
public class StarBadgeController {

    @Autowired
    StarBadgeRepository starBadgeRepository;
    @Autowired
    StarBadgeService starBadgeService;

    @GetMapping
    public ResponseEntity<List<StarBadge>> getStarBadge() {
        List<StarBadge> starBadge = starBadgeService.getStarBadge();
        return ResponseEntity.ok(starBadge);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStarBadge(@PathVariable Integer id) {
        ResponseDTO responseDTO = starBadgeService.getStarBadgeById(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addStarBadge(@Valid @RequestBody StarBadgeDTO starBadgeDTO) {
        ResponseDTO responseDTO = starBadgeService.addStarBadge(starBadgeDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseDTO> editStarBadge(@PathVariable Integer id, @Valid @RequestBody StarBadgeDTO starBadgeDTO) {
        ResponseDTO responseDTO = starBadgeService.editStarBadge(id, starBadgeDTO);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDTO> deleteStarBadge(@PathVariable Integer id) {
        ResponseDTO responseDTO = starBadgeService.deleteStarBadge(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(responseDTO);
    }
}
