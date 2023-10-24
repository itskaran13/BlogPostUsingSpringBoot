package com.mountblue.Blog.repository;

import com.mountblue.Blog.entities.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {


    @Query("SELECT bp FROM PostEntity bp  join bp.tags t   WHERE " +
            "LOWER(bp.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(bp.excerpt) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(bp.author) " +
            " LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<PostEntity> searchQuery(@Param("searchTerm") String keyword);


    @Query("SELECT p FROM PostEntity p " +
            "LEFT JOIN p.tags t " +
            "WHERE (:authors IS NULL OR p.author IN :authors) " +
            "AND (:tags IS NULL OR t.name IN :tags)")
    List<PostEntity> findByAuthorsTagsAndPublishDate(
            @Param("authors") String[] authors,
            @Param("tags") String[] tags
            );
}


//    @Query("SELECT p FROM PostEntity p " +
//            "JOIN p.tags t " +
//            "WHERE (:authorList IS NULL OR LOWER(p.author) IN :authors) "+
//            "AND (:tagList IS NULL OR LOWER(t.name) IN :tags) "+
//            "AND (LOWER(p.title) LIKE :keyword OR " +
//            "LOWER(p.content) LIKE %:keyword% OR " +
//            "LOWER(p.author) LIKE :keyword OR " +
//            "LOWER(t.name) LIKE :keyword)
//    List<PostEntity> findByAuthorsTagsAndPublishDate(@Param("keyword") String keyword,@Param("authors") String[] authors,@Param("tags") String[] tags);
//    }







