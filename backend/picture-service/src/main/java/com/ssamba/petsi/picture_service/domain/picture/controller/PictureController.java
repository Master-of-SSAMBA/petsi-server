package com.ssamba.petsi.picture_service.domain.picture.controller;

import com.ssamba.petsi.picture_service.domain.picture.dto.request.PictureMonthlyRequestDto;
import com.ssamba.petsi.picture_service.domain.picture.dto.response.PictureResponseDto;
import com.ssamba.petsi.picture_service.domain.picture.service.PictureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/picture")
@Tag(name = "PictureRestController", description = "사진 인증 관리")
public class PictureController {

    private final PictureService pictureService;

    // 사용자 사진 인증 전체 조회
    @GetMapping
    public ResponseEntity<?> findAll(@RequestHeader("X-User-Id") Long userId, @RequestParam(name = "page", defaultValue = "0") int page) {
        List<PictureResponseDto.Response> pictures = pictureService.getPictures(userId, page);
        return ResponseEntity.ok(pictures);
    }

    // 사진 인증 등록
    @PostMapping
    public ResponseEntity<?> createPicture(@RequestHeader("X-User-Id") Long userId, @RequestPart("img") MultipartFile file, @RequestPart("content") String content) {
        // 파일이 null인지 또는 비어 있는지 확인
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 업로드되지 않았습니다.");
        }

        pictureService.savePicture(userId, file, content);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 이자율 및 인증 횟수 조회
    @GetMapping("/count")
    public ResponseEntity<?> findPictureCount(@RequestHeader("X-User-Id") Long userId) {
        PictureResponseDto.Count count = pictureService.getCount(userId);
        return ResponseEntity.ok(count);
    }

    // 사진 인증 상세 조회
    @GetMapping("{pictureId}")
    public ResponseEntity<?> findPicture(@RequestHeader("X-User-Id") Long userId, @PathVariable("pictureId") Long pictureId) {
        PictureResponseDto.Detail detail = pictureService.getPicture(userId, pictureId);
        return ResponseEntity.ok(detail);
    }

    @PostMapping("/get-monthly-picture")
    public List<Integer> getMonthlyPicture(@RequestBody PictureMonthlyRequestDto req) {
        return pictureService.getMonthlyPictures(req.getYear(), req.getMonth(), req.getUserId());
    }

}
