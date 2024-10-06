package com.ssamba.petsi.picture_service.domain.picture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PredictResponse {
    private boolean isPet;
    private double confidence;
}
