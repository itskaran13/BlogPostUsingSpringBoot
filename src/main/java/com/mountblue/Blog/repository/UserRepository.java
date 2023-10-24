package com.mountblue.Blog.repository;

import com.mountblue.Blog.entities.PostEntity;
import com.mountblue.Blog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {

        UserEntity findByUsername(String username);
        @Query("SELECT p FROM UserEntity u "+
                "JOIN u.posts p "+
                "WHERE u.username=:user")
        List<PostEntity> findUserPosts(String user);

        UserEntity findByEmail(String email);

}
