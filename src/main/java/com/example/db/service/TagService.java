package com.example.db.service;

import com.example.db.model.Tag;
import com.example.db.model.converters.TagConverter;
import com.example.db.model.dto.TagDto;
import com.example.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagDto save(TagDto dto) {
        Tag tag = TagConverter.toTag(dto);
        tag = tagRepository.save(tag);
        return TagConverter.toDto(tag);
    }

    public List<TagDto> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(TagConverter::toDto)
                .collect(Collectors.toList());
    }

    public TagDto findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.map(TagConverter::toDto).orElse(null);
    }

    public TagDto findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        return tag.map(TagConverter::toDto).orElse(null);
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}