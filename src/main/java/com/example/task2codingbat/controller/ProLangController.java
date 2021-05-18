package com.example.task2codingbat.controller;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.repository.ProLangRepository;
import com.example.task2codingbat.service.ProLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/progLanguages")
public class ProLangController {

    @Autowired
    ProLangService proLangService;
    @Autowired
    ProLangRepository proLangRepository;

    @GetMapping
    public ResponseEntity<List<ProgrammingLanguage>> getAllProgLangs() {
        List<ProgrammingLanguage> programmingLanguageList = proLangService.getAllProgLangs();
        return ResponseEntity.ok(programmingLanguageList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProgLangByID(@PathVariable Integer id) {
        ResponseDTO responseDTO = proLangService.getProgLangByID(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> addProgLang(@Valid @RequestBody ProgrammingLanguage programmingLanguage) {
        ResponseDTO responseDTO = proLangService.addProgLang(programmingLanguage);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editProgLang(@PathVariable Integer id, @Valid @RequestBody ProgrammingLanguage programmingLanguage) {
        ResponseDTO responseDTO = proLangService.editProgLang(id, programmingLanguage);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProgLang(@PathVariable Integer id) {
        ResponseDTO responseDTO = proLangService.deleteProgLang(id);
        return ResponseEntity.status(responseDTO.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(responseDTO);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}

