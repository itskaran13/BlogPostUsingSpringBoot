package com.mountblue.Blog.service;

import com.mountblue.Blog.entities.CommentEntity;
import com.mountblue.Blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
  @Autowired
  public CommentRepository commentRepository;

  public CommentEntity addBlog(CommentEntity commentEntity) {
    return commentRepository.save(commentEntity);
  }

public List<CommentEntity> getCommentEntityList(){
    List<CommentEntity> list = commentRepository.findAll();
    return list;
}

    public void delete(Long postId) {
        commentRepository.deleteById(postId);
    }
    public CommentEntity getCommentById(Long comment_id) {
        return commentRepository.findById(comment_id).get();
    }
    public CommentEntity updateComment(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }


}
