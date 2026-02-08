package com.example.blink.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    // 인증/인가 (4xx)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    // 회원 관련 (4xx)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_NAME(HttpStatus.CONFLICT, "이미 사용 중인 이름입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // 게시물 관련 (4xx)
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
    POST_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "본인의 게시물만 삭제할 수 있습니다."),
    POST_IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "사진은 최소 1장 이상 필요합니다."),
    POST_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "게시물 내용은 필수입니다."),
    POST_CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "게시물 내용은 500자를 초과할 수 없습니다."),
    POST_IMAGE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "사진은 최대 3장까지만 첨부할 수 있습니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;
}