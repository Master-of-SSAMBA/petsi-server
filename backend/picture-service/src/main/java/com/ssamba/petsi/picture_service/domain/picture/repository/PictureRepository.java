package com.ssamba.petsi.picture_service.domain.picture.repository;

import com.ssamba.petsi.picture_service.domain.picture.entity.Picture;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Page<Picture> findAllByUserId(long userId, Pageable pageable);

    long countByUserId(Long userId);

    @Query("SELECT p FROM Picture p WHERE p.userId = :userId " +
            "AND YEAR(p.createdAt) = :year AND MONTH(p.createdAt) = :month")
    List<Picture> findByUserIdAndYearAndMonth(@Param("userId") Long userId,
                                              @Param("year") int year,
                                              @Param("month") int month);
}
