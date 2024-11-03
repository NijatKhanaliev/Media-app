package com.company.media.repository;

import com.company.media.model.MediaPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaPostRepository extends JpaRepository<MediaPost, Long> {
    @Query("select m from MediaPost m where m.name like concat(:name,'%')")
    List<MediaPost> searchByName(@Param("name") String name);
}