package com.ssamba.petsi.expense_service.global.client;

import com.ssamba.petsi.expense_service.domain.expense.dto.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/user/expense-info")
    UserDto getUser(@RequestParam Long userId);

}
