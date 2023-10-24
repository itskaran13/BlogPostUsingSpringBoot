package com.mountblue.Blog.controller;

import com.mountblue.Blog.entities.CommentEntity;
import com.mountblue.Blog.entities.PostEntity;
import com.mountblue.Blog.service.CommentService;
import com.mountblue.Blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    PostService postService;



    @PostMapping("/add-comment")
    public String  addComment( @ModelAttribute  CommentEntity commentEntity){
        commentEntity.setUpdatedAt(new Date());
        commentEntity.setCreatedAt(new Date());
        commentService.addBlog(commentEntity);
        return "redirect:/comment";

    }

    @RequestMapping   (value = "/DeleteComment/{id}/{comment_id}",method = RequestMethod.GET)
    public String Delete(@PathVariable  Long comment_id, @PathVariable String id){
        commentService.delete(comment_id);
        return "redirect:/viewpost?id=" + id;
    }

    @RequestMapping ("/UpdateComment")
    public String updateComment(@RequestParam Long id, @RequestParam Long postId ,  Model model ){
        CommentEntity retreivedComment = commentService.getCommentById(id);
        model.addAttribute("UpdateComment" , retreivedComment);
        model.addAttribute("post_id", postId);
        return "UpdateComment";
    }


    @RequestMapping ("/updateComment/{comment_id}")
    public String update(@ModelAttribute CommentEntity commentEntity,
                         @RequestParam("updatedComment") String comment,
                         @RequestParam("post_id") long postId,
                         @PathVariable  Long comment_id
                         ){
        CommentEntity exitPost = commentService.getCommentById(comment_id);
        exitPost.setCommentText(comment);
        exitPost.setUpdatedAt(new Date());
        commentService.updateComment(exitPost);
        return "redirect:/viewpost?id=" + postId;
    }

    @PostMapping  ("/Posts/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam("name") String name, @RequestParam("email") String email,
                             @RequestParam("comment") String comment) {

        PostEntity post = postService.getPostById(id);
        CommentEntity newComment = new CommentEntity();
        newComment.setName(name);
        newComment.setEmail(email);
        newComment.setCommentText(comment);
        newComment.setPost(post);
        newComment.setCreatedAt(new Date());
        newComment.setUpdatedAt(new Date());
        post.addComment(newComment);

        postService.addBlog(post);
        return "redirect:/viewpost?id={id}";
    }
}
