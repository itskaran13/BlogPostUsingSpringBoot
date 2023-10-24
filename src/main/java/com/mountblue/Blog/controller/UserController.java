package com.mountblue.Blog.controller;
import com.mountblue.Blog.entities.UserEntity;
import com.mountblue.Blog.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

@Autowired
private UserServices userService;

@Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/loginform")
    public String loginForm() {

        return "Login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("inputUser", new UserEntity());


        return "Login_form";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute UserEntity userEntity){
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        userService.addBlog(userEntity);
        return("redirect:/");
}

@PostMapping("/myblogs")
    public String myBlogs(Model model){
        model.addAttribute("blogPosts",userService.getUsersPosts());
        return "MyBlogPost";
}




}
