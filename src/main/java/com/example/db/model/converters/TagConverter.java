package com.example.db.model.converters;

import com.example.db.model.Tag;
import com.example.db.model.dto.TagDto;

public class TagConverter {

    public static Tag toTag(TagDto dto) {
        return Tag.builder()
                .name(dto.name())
                .id(dto.id())
                .build();
    }

    public static TagDto toDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }
}
