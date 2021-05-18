package com.example.task2codingbat.service;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.repository.ProLangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProLangService {
    @Autowired
    ProLangRepository proLangRepository;


    public List<ProgrammingLanguage> getAllProgLangs() {
        return proLangRepository.findAll();
    }

    public ResponseDTO getProgLangByID(Integer id) {
        Optional<ProgrammingLanguage> languageOptional = proLangRepository.findById(id);
        return languageOptional.map(programmingLanguage -> new ResponseDTO("Found !", true, programmingLanguage)).orElseGet(() -> new ResponseDTO("Not found !", false, null));
    }

    public ResponseDTO addProgLang(ProgrammingLanguage programmingLanguage) {
        Optional<ProgrammingLanguage> byName = proLangRepository.findByName(programmingLanguage.getName());
        if (byName.isPresent())
            return new ResponseDTO("Programming language with name: '" + programmingLanguage.getName() + "' " +
                    "already exists !", false, byName.get());

        ProgrammingLanguage newProgLang = new ProgrammingLanguage(programmingLanguage.getName());
        ProgrammingLanguage save = proLangRepository.save(newProgLang);
        return new ResponseDTO("Programming language with name: '" + save.getName() + "' successfully added !", true, save);
    }

    public ResponseDTO editProgLang(Integer id, ProgrammingLanguage programmingLanguage) {
        Optional<ProgrammingLanguage> byId = proLangRepository.findById(id);
        if (!byId.isPresent())
            return new ResponseDTO("Programming language with id:'" + id + "' not found !", false);

        Optional<ProgrammingLanguage> byName = proLangRepository.findByName(programmingLanguage.getName());
        if (byName.isPresent()) {
            if (!byName.get().getId().equals(id)) {
                return new ResponseDTO("Programming language with name:'" + byName.get().getName() + "' already exists!", false, byName.get());
            } else {
                return new ResponseDTO("Programming language name was not changed ! ", false);
            }
        }

        ProgrammingLanguage editProgLang = byId.get();
        editProgLang.setName(programmingLanguage.getName());
        ProgrammingLanguage save = proLangRepository.save(editProgLang);
        return new ResponseDTO("Programming language with id:'" + id + "' successfully edited !", true, save);
    }

    public ResponseDTO deleteProgLang(Integer id) {
        if (proLangRepository.existsById(id)) {
            proLangRepository.deleteById(id);
            return new ResponseDTO("Programming language with id:'" + id + "' successfully deleted !", false);
        }
        return new ResponseDTO("Programming language with id:'" + id + "' not found!", true);
    }
}
