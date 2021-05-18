package com.example.task2codingbat.service;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import com.example.task2codingbat.entity.StarBadge;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.StarBadgeDTO;
import com.example.task2codingbat.repository.ProLangRepository;
import com.example.task2codingbat.repository.StarBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarBadgeService {
    @Autowired
    StarBadgeRepository starBadgeRepository;
    @Autowired
    ProLangRepository proLangRepository;

    public List<StarBadge> getStarBadge() {
        return starBadgeRepository.findAll();
    }

    public ResponseDTO getStarBadgeById(Integer id) {
        Optional<StarBadge> starBadgeOptional = starBadgeRepository.findById(id);
        return starBadgeOptional.map(starBadge -> new ResponseDTO("Found !", true, starBadge)).orElseGet(() -> new ResponseDTO("Not found !", false));
    }

    public ResponseDTO addStarBadge(StarBadgeDTO starBadgeDTO) {
        StarBadge starBadge = new StarBadge();
        Optional<ProgrammingLanguage> optionalProgLang = proLangRepository.findById(starBadgeDTO.getProLangID());
        if (!optionalProgLang.isPresent()) {
            return new ResponseDTO("Programming language with id:'" + starBadgeDTO.getProLangID() + " not found!", false);
        }

        starBadge.setLanguage(optionalProgLang.get());
        starBadge.setValue(starBadgeDTO.getValue());
        starBadgeRepository.save(starBadge);
        return new ResponseDTO("New star badge added!", true);
    }

    public ResponseDTO editStarBadge(Integer id, StarBadgeDTO starBadgeDTO) {
        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(id);
        if (!optionalStarBadge.isPresent())
            return new ResponseDTO("Star badge with id:'" + id + "' not found!", false);

        StarBadge starBadge = optionalStarBadge.get();
        Optional<ProgrammingLanguage> optionalProgLang = proLangRepository.findById(starBadgeDTO.getProLangID());
        if (!optionalProgLang.isPresent()) {
            return new ResponseDTO("Programming language with id: '" + starBadgeDTO.getProLangID() + "' not found!", false);
        }
        starBadge.setLanguage(optionalProgLang.get());
        starBadge.setValue(starBadgeDTO.getValue());
        starBadgeRepository.save(starBadge);
        return new ResponseDTO("Star badge with id:'" + id + "' successfully edited!", true);
    }

    public ResponseDTO deleteStarBadge(Integer id) {
        if (starBadgeRepository.existsById(id)) {
            starBadgeRepository.deleteById(id);
            return new ResponseDTO("Star badge with id:'" + id + "' successfully deleted !", true);
        }
        return new ResponseDTO("Star badge with id:'" + id + "' not found !", false);

    }
}
