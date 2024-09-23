package com.ssamba.petsi.picture_service.domain.picture.repository;

import com.ssamba.petsi.picture_service.domain.picture.entity.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Page<Picture> findAllByUserId(long userId, Pageable pageable);

    long countByUserId(Long userId);
}
