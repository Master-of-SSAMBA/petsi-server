package com.ssamba.petsi.expense_service.domain.expense.enums;

import lombok.Getter;

@Getter
public enum ProductCategory {

    FOOD("사료"),
    SNACK("간식"),
    TOY("장난감"),
    PRODUCT("물품");

    private final String value;

    ProductCategory(String value) {
        this.value = value;
    }

}
