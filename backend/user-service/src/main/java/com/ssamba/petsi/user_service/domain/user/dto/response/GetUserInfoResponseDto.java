package com.ssamba.petsi.user_service.domain.user.dto.response;

import java.util.List;

import com.ssamba.petsi.user_service.domain.user.entity.User;
import com.ssamba.petsi.user_service.global.dto.PetCustomDto;

import lombok.Getter;

@Getter
public class GetUserInfoResponseDto {
	private Long userId;
	private String nickname;
	private String email;
	private String profileImage;
	private List<PetCustomDto> pets;

	public GetUserInfoResponseDto(User user, List<PetCustomDto> petList) {
		this.userId = user.getUserId();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.profileImage = user.getProfileImage();
		this.pets = petList;
	}
}
