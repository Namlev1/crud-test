package com.example.db.controllers;

import com.example.db.model.dto.TagDto;
import com.example.db.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping(value = "/", consumes = {"application/json"})
    public TagDto save(@RequestBody TagDto tag) {
        return tagService.save(tag);
    }

    @GetMapping("/all")
    public List<TagDto> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/id/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @GetMapping("/name/{name}")
    public TagDto getTagByName(@PathVariable String name) {
        return tagService.findByName(name);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

}
