package com.mountblue.Blog.repository;

import com.mountblue.Blog.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity,Long>{
    TagEntity findByName(String name);
}
