package com.ssamba.petsi.pet_service.domain.pet.enums;

import lombok.Getter;

@Getter
public enum PetStatus {
    ACTIVATED("활성"),
    INACTIVATED("비활성");

    private final String value;

    PetStatus(String value) {
        this.value = value;
    }
}
