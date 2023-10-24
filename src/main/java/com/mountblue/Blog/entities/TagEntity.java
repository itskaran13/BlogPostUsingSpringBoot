package com.mountblue.Blog.entities;

    import jakarta.persistence.*;
    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

@Entity
@Table(name = "Tag")
    public class TagEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToMany(mappedBy = "tags")
        private Set<PostEntity> posts = new HashSet<>();

        private String name;

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

        @Column(name = "created_at")
        private LocalDate createdAt = LocalDate.now();
        @Column(name = "updated_at")
        private LocalDate updatedAt = LocalDate.now();


        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
        @Override
        public String toString(){
            return name;
        }
    }



