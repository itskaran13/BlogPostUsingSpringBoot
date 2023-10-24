package com.mountblue.Blog.controller;
import com.mountblue.Blog.entities.PostEntity;
import com.mountblue.Blog.entities.TagEntity;
import com.mountblue.Blog.repository.PostRepository;
import com.mountblue.Blog.security.CustomUserDetails;
import com.mountblue.Blog.service.PostService;
import com.mountblue.Blog.service.TagService;
import com.mountblue.Blog.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserServices userServices;


    @RequestMapping("/viewpost")
    public String postEntity(@RequestParam Long id, Model model) {
        PostEntity retreivedPost = postService.getPostById(id);
        CustomUserDetails currentUser = userServices.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getEmail());
            model.addAttribute("admin",currentUser.isAdmin());
        }

        model.addAttribute("posts", retreivedPost);
        model.addAttribute("comments", retreivedPost.getComments());
        return "ViewMore";
    }

    @RequestMapping(value = "/add-blog", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute PostEntity postEntity, @RequestParam("new_tags") String tags) {
        CustomUserDetails currentUser = userServices.getCurrentUser();

        Set<TagEntity> tagList = Arrays.stream(tags.split(","))
                .map(tagName -> tagService.saveTag(tagName.trim()))
                .collect(Collectors.toSet());
        postEntity.setTags(tagList);
        postEntity.setAuthor(currentUser.getUsername());
        postService.addBlog(postEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "/show-blog", method = RequestMethod.GET)
    public String showBlogPost(Model model, PostEntity postEntity) {
        CustomUserDetails currentUser = userServices.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getUsername());
        }
        LocalDate date  = LocalDate.now();
        model.addAttribute("published" ,date);
        return "WriteBlog";
    }



    @RequestMapping("/Updatepost")
    public String UpdatePost(@RequestParam Long id, Model model) {
        PostEntity retreivedPost = postService.getPostById(id);
        model.addAttribute("UpdatedPost", retreivedPost);
        return "UpdatPost";
    }


    @RequestMapping("/update-post")
    public String update(@ModelAttribute PostEntity postEntity, @RequestParam("updated_tags") String tags) {
        PostEntity exitPost = postRepository.findById(postEntity.getId()).get();
        exitPost.setExcerpt(postEntity.getExcerpt());
        exitPost.setUpdateTime(LocalDate.now());
        exitPost.setContent(postEntity.getContent());
        exitPost.setTitle(postEntity.getTitle());
        exitPost.setAuthor(postEntity.getAuthor());
        Set<TagEntity> tagList = Arrays.stream(tags.split(","))
                .map(tagName -> tagService.saveTag(tagName.trim())).collect(Collectors.toSet());
        postEntity.setTags(tagList);
        exitPost.setTags(postEntity.getTags());
        postService.update(exitPost);
        return "redirect:/";
    }

    @RequestMapping(value = "/DeletePost/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/";
    }
    @GetMapping
    public String findNoOfPages(@RequestParam(value = "start", required = false) Integer pageNo,
                                @RequestParam(required = false, value = "sort") String order, Model model
    ) {

        int pageSize = 10;
        if (pageNo == null) {
            pageNo = 1;
        }
        CustomUserDetails currentUser = userServices.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser.getUsername());
        }
        Page<PostEntity> page = postService.findPage(pageNo, pageSize, order);
        List<PostEntity> blogList = page.getContent();
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("blogPosts", blogList);
        model.addAttribute("sortDir", order);

        return "BlogPost";
    }

    @GetMapping("/add-filters")
    public String addFilters(Model model) {
        model.addAttribute("authors", postService.getAuthorList());
        model.addAttribute("tags", tagService.getAllTags());
        return "Filter";
    }

    @GetMapping("/search")
    public String getBySearching(@RequestParam("search") String keyword, Model model) {
        List<PostEntity> blogPost = postService.searchBlogPosts(keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("blogPosts", blogPost);
        return "BlogPost";
    }

    @GetMapping("/filtered-blogs")
    public String filterPosts(
            @RequestParam(value = "authors", required = false) String[] authorValues,
            @RequestParam(value = "tags", required = false) String[] tagValues,
            Model model
    ) {
        List<PostEntity> filteredPosts = postService.filterPosts(tagValues, authorValues);
        model.addAttribute("blogPosts", filteredPosts);
        return "BlogPost";
    }



    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";

    }

}


