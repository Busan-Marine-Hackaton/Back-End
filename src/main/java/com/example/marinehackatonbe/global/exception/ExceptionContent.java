package com.example.marinehackatonbe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ExceptionContent {
	NOT_FOUND_MEMBER(NOT_FOUND, "멤버가 존재하지 않습니다." ),
	NOT_FOUND_PRODUCT(NOT_FOUND, "상품이 존재하지 않습니다." ),
	NOT_FOUND_ENTERPRISE(NOT_FOUND, "기업이 존재하지 않습니다." ),
	WRONG_PASSWORD(NOT_FOUND, "잘못된 패스워드 입니다." ),
	FAIL_PHOTO_UPLOAD(NOT_FOUND, "사진 업로드에 실패하였습니다." ),
	FAIL_PURCHASE(NOT_FOUND, "물건 구매에 실패하였습니다." );

	private final HttpStatus httpStatus;
	private final String message;
}
