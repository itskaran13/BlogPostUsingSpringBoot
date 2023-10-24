package com.mountblue.Blog.service;

import com.mountblue.Blog.entities.PostEntity;
import com.mountblue.Blog.entities.TagEntity;
import com.mountblue.Blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

        public TagEntity saveTag(String tagName) {
            TagEntity existingTag = tagRepository.findByName(tagName);
            if (existingTag != null) {
                return existingTag;
            } else {
                TagEntity newTag = new TagEntity();
                newTag.setName(tagName);
                return tagRepository.save(newTag);

            }

    }
    public Set<String> getAllTags() {
        List<TagEntity> tags = tagRepository.findAll();
        // Convert the Tag entities to a list of tag names.
        Set<String> tagNames = tags.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
        return tagNames;
    }

}
