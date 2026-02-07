package com.example.blink.exception;

import com.example.blink.exhandler.ErrorCode;
import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}