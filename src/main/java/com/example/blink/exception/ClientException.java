package com.example.blink.exception;

import com.example.blink.exhandler.ErrorCode;
import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {

    private ErrorCode errorCode;

    public ClientException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}