package com.mountblue.Blog.service;

import com.mountblue.Blog.entities.PostEntity;
import com.mountblue.Blog.entities.UserEntity;
import com.mountblue.Blog.repository.UserRepository;
import com.mountblue.Blog.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServices implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity addBlog(UserEntity userEntity) {
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());
        if(user != null){
            return user;
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);
    }
    public List<PostEntity> getUsersPosts() {
        List<PostEntity> postList = new ArrayList<>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        postList = userRepository.findUserPosts(user);
        return postList;
    }

    public CustomUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }

        // Handle cases where the principal is not a CustomUserDetails as needed.
        return null;
    }
}

