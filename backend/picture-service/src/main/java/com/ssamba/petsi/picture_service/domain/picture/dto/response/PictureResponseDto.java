package com.ssamba.petsi.picture_service.domain.picture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PictureResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long pictureId;
        private String img;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private Long pictureId;
        private String img;
        private String content;
        private DateResponseDto registDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Count {
        private Long pictureCnt;
    }

}
