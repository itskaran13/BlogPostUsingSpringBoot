package com.mountblue.Blog.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )






    private Set<TagEntity> tags = new HashSet<>();
    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "excerpt", columnDefinition = "TEXT")
    private String excerpt;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "published_at")

    LocalDate publishedTime= LocalDate.now();

    @Column(name = "is_published")
    private boolean is_published;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "created_at")
    LocalDate createTime = LocalDate.now();



    @Column(name = "updated_at")
    LocalDate updateTime = LocalDate.now();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalDate publishedTime) {
        this.publishedTime = publishedTime;
    }

    public boolean isIs_published() {
        return is_published;
    }

    public void setIs_published(boolean is_published) {
        this.is_published = is_published;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", published_at='" + publishedTime+ '\'' +
                ", is_published=" + is_published +
                ", createTime=" + createTime +
                ", updateTIme=" + updateTime +
                '}';
    }

    public void addComment(CommentEntity comment){
        if(comments == null){
            comments = new ArrayList<>();
        }
        comment.setPost(this);
        comments.add(comment);
    }
}

