package com.example.task2codingbat.service;

import com.example.task2codingbat.entity.ProgrammingLanguage;
import com.example.task2codingbat.entity.Topic;
import com.example.task2codingbat.payload.ResponseDTO;
import com.example.task2codingbat.payload.TopicDTO;
import com.example.task2codingbat.repository.ProLangRepository;
import com.example.task2codingbat.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ProLangRepository proLangRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public ResponseDTO getTopicById(Integer id) {
        Optional<Topic> byId = topicRepository.findById(id);
        return byId.map(topic -> new ResponseDTO("Found !", true, topic)).orElseGet(() -> new ResponseDTO("Not found !", false));
    }

    public ResponseDTO addTopic(TopicDTO topicDTO) {
        if (topicRepository.existsByName(topicDTO.getName()))
            return new ResponseDTO("Category with name: '" + topicDTO.getName() + "' already exists !", false);

        List<Integer> proLangIDs = topicDTO.getProLangIDs();
        List<ProgrammingLanguage> newProLangs = new ArrayList<>();
        for (Integer proLangID : proLangIDs) {
            Optional<ProgrammingLanguage> languageOptional = proLangRepository.findById(proLangID);
            if (!languageOptional.isPresent()) {
                return new ResponseDTO("Programming language with id:'" + proLangID + "' not found !", false);
            }
            newProLangs.add(languageOptional.get());
        }
        Topic newTopic = new Topic(topicDTO.getName(), topicDTO.getDescription(), topicDTO.getStarNumber(), newProLangs);
        Topic save = topicRepository.save(newTopic);
        return new ResponseDTO("Topic with name: '" + topicDTO.getName() + "' successfully added !", true, save);
    }

    public ResponseDTO editTopic(Integer id, TopicDTO topicDTO) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (!topicOptional.isPresent())
            return new ResponseDTO("Topic with id:'" + id + "' not found !", false);

        Optional<Topic> nameAndIdNot = topicRepository.findByNameAndIdNot(topicDTO.getName(), id);
        if (nameAndIdNot.isPresent())
            return new ResponseDTO("Topic with name:'" + topicDTO.getName() + "' already exists !", false, nameAndIdNot.get());

        List<Integer> proLangIDs = topicDTO.getProLangIDs();
        List<ProgrammingLanguage> editProLangs = new ArrayList<>();
        for (Integer proLangID : proLangIDs) {
            Optional<ProgrammingLanguage> languageOptional = proLangRepository.findById(proLangID);
            if (!languageOptional.isPresent()) {
                return new ResponseDTO("Programming language with id:'" + proLangID + "' not found !", false);
            }
            editProLangs.add(languageOptional.get());
        }

        Topic editTopic = topicOptional.get();
        editTopic.setName(topicDTO.getName());
        editTopic.setDescription(topicDTO.getDescription());
        editTopic.setStarNumber(topicDTO.getStarNumber());
        editTopic.setLanguageList(editProLangs);
        Topic save = topicRepository.save(editTopic);
        return new ResponseDTO("Topic with id:'" + id + "'successfully edited !", true, save);
    }

    public ResponseDTO deleteTopic(Integer id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return new ResponseDTO("Topic with id:'" + id + "' successfully deleted", true);
        }
        return new ResponseDTO("Topic with id:'" + id + "'  not found !", false);
    }
}
