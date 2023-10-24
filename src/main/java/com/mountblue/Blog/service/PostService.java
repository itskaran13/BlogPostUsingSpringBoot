    package com.mountblue.Blog.service;

    import com.mountblue.Blog.entities.PostEntity;
    import com.mountblue.Blog.entities.TagEntity;
    import com.mountblue.Blog.repository.PostRepository;
    import com.mountblue.Blog.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Service
    public class PostService {

        @Autowired
        private PostRepository postRepository;
        @Autowired
        private UserRepository userRepository;

        public PostEntity getPostById(Long id) {

            PostEntity post = postRepository.findById(id).get();
            return post;
        }
        public List<PostEntity> getAllPost(){
           return postRepository.findAll();

        }
        public PostEntity addBlog(PostEntity postEntity) {
            postEntity.setUser(
                    userRepository.findByUsername(postEntity.getAuthor())
            );
            return postRepository.save(postEntity);
        }


        public PostEntity update(PostEntity postEntity) {
            return postRepository.save(postEntity);
        }

        public void delete(Long postId) {

            postRepository.deleteById(postId);
            }

        public List<PostEntity> searchBlogPosts(String searchQuery) {
            String[] searchTerms = searchQuery.split(" ");
            List<PostEntity> matchingPosts = new ArrayList<>();
            for (String term : searchTerms) {
                matchingPosts.addAll(postRepository.searchQuery(term));
            }
            return matchingPosts;
        }

        public Page<PostEntity> findPage(int pageNo, int pageSize, String sort) {
            // Create a Pageable object with sorting
            Pageable pageable;
            if (sort != null) {
                switch (sort) {
                    case "publishedDesc":
                        pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("publishedTime")));
                        break;
                    case "titleAsc":
                        pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("title")));
                        break;
                    case "titleDesc":
                        pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("title")));
                        break;
                    default:
                        pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("publishedTime")));
                        break;
                }
            } else {
                pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("publishedTime")));
            }

            return postRepository.findAll(pageable);
        }


        public Set<String> getAuthorList(){
            List<PostEntity> posts = postRepository.findAll();
            Set<String> authorList = new HashSet<>();
            if(!authorList.contains('[')) {
                posts.forEach(p -> authorList.add(p.getAuthor()));
            }
            return authorList;
                }


        public List<PostEntity> filterPosts(String[] authors, String[] tags) {
            return postRepository.findByAuthorsTagsAndPublishDate(tags,authors);
        }




    }







