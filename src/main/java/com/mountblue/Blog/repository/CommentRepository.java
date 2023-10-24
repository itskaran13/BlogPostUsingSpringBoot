package com.mountblue.Blog.repository;
import com.mountblue.Blog.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<CommentEntity, Long> {
}
