package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.request.ChangePasswordDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.PatchNicknameDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.RegisterKeycloakUserRequestDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.SignupRequestDto;
import com.ssamba.petsi.user_service.domain.user.dto.response.ExpenseInfoResponseDto;
import com.ssamba.petsi.user_service.domain.user.dto.response.GetUserInfoResponseDto;
import com.ssamba.petsi.user_service.domain.user.entity.User;
import com.ssamba.petsi.user_service.domain.user.enums.UserStatus;
import com.ssamba.petsi.user_service.domain.user.repository.UserRepository;
import com.ssamba.petsi.user_service.global.client.PetClient;
import com.ssamba.petsi.user_service.global.dto.PetCustomDto;
import com.ssamba.petsi.user_service.global.dto.PetResponseDto;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final FinApiService finApiService;
    private final S3Service s3Service;
    private final PetClient petClient;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        isValidSignup(signupRequestDto);
        String userKey = finApiService.addMember(signupRequestDto.getEmail());
        User newUser = SignupRequestDto.toEntity(signupRequestDto, userKey);
        userRepository.save(newUser);
        keycloakService.registerUserInKeycloak(
                RegisterKeycloakUserRequestDto.create(signupRequestDto, newUser.getUserId(), userKey));
    }

    @Transactional
    public void changeNickname(Long userId, PatchNicknameDto patchNicknameDto) {
        User user = getUserById(userId);
        user.setNickname(patchNicknameDto.getNickname());
    }

    private User getUserById(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        isLeaveUser(user);
        return user;
    }

    private void isLeaveUser(User user) {
        if (( UserStatus.INACTIVATED.getValue().equals(user.getStatus()))) {
            throw new BusinessLogicException(ExceptionCode.LEAVE_USER);
        }
    }

    @Transactional
    public void changeImg(Long userId, MultipartFile file) {
        User user = getUserById(userId);
        // 사진 저장 로직
        // 사진 확장자인지 확인
        if(!isValidImageMimeType(file)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_FILE_FORM);
        }

        // 사진 업로드
        try {
            // 파일명 지정 (겹치면 안되고, 확장자 빼먹지 않도록 조심!)
            String fileName = UUID.randomUUID() + file.getOriginalFilename();
            // 파일데이터와 파일명 넘겨서 S3에 저장
            String url = s3Service.upload(file, fileName);
            user.setProfileImage(url);
        } catch (Exception e) {
            // 사진 업로드 오류 처리
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void isValidSignup(SignupRequestDto signupRequestDto) {
        isValidEmail(signupRequestDto.getEmail());
        isValidNickname(signupRequestDto.getNickname());
    }

    public void isValidEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_EMAIL);
        }
    }

    private void isValidNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATED_NICKNAME);
        }
    }

    // 확장자 확인 메서드
    private boolean isValidImageMimeType(MultipartFile file) {
        // 허용되는 MIME 타입 목록
        String[] validMimeTypes = {"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp", "image/tiff"};

        // MIME 타입 확인
        String mimeType = file.getContentType();

        // MIME 타입이 허용된 목록에 있는지 확인
        return mimeType != null && Arrays.asList(validMimeTypes).contains(mimeType);
    }

    @Transactional(readOnly = true)
	public GetUserInfoResponseDto getUserInfo(Long userId) {
        User user = getUserById(userId);

        List<PetResponseDto> petList = petClient.findPetForUserInfo(userId);

        return new GetUserInfoResponseDto(user, petList);
	}

    @Transactional(readOnly = true)
    public ExpenseInfoResponseDto getExpenseInfo(Long userId) {
        User user = getUserById(userId);

        return new ExpenseInfoResponseDto(user.getNickname(), user.getProfileImage());
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        keycloakService.changePassword(getUserById(userId).getEmail()
                , changePasswordDto.getPassword());
    }

    @Transactional
    public void leave(Long userId) {
        User user = getUserById(userId);
        user.setStatus(UserStatus.INACTIVATED.getValue());
        keycloakService.deactivateUser(user.getEmail());
    }
}
