package com.example.blink.exhandler;

import com.example.blink.exception.ClientException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@ControllerAdvice(basePackages = "com.example.blink.web.controller.view")
public class ViewExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleClientException(ClientException e, HttpServletResponse response) {
        try {
            log.warn("ViewException: {}", e.getMessage());
            response.sendError(e.getErrorCode().getHttpStatus().value(), e.getMessage());
            return new ModelAndView();
        } catch (IOException ex) {
            log.error("resolver ex", ex);
        }

        return null;
    }

    @ExceptionHandler
    public ModelAndView handleException(Exception e, HttpServletResponse response) {
        try {
            log.error("Unexpected error in view: {}", e.getMessage(), e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다.");
            return new ModelAndView();
        } catch (IOException ex) {
            log.error("resolver ex", ex);
        }

        return null;
    }
}