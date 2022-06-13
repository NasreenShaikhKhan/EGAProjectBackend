package bankingsystem.backend.controller;

import bankingsystem.backend.dto.CustomErrorDto;
import bankingsystem.backend.exception.ApplicationException;
import bankingsystem.backend.exception.BadRequestException;
import bankingsystem.backend.exception.JwtTokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    private final String signature = "dfdv-hdfsdgfhsd+dsdgsdfg";


    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<CustomErrorDto> handleException(Exception e) {
        log.error("Global Exception", e);
        CustomErrorDto errResp = new CustomErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(errResp, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<CustomErrorDto> handleException(BadRequestException e) {
        log.error("BadRequestException ", e);
        CustomErrorDto errResp = new CustomErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(errResp, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = JwtTokenExpiredException.class)
    public ResponseEntity<CustomErrorDto> handleException(JwtTokenExpiredException e) {
        log.error("Token expired exception", e);
        CustomErrorDto errResp = new CustomErrorDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return new ResponseEntity<>(errResp, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = SecurityException.class)
    public ResponseEntity<CustomErrorDto> handleException(SecurityException e) {
        log.error("Token expired exception", e);
        CustomErrorDto errResp = new CustomErrorDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return new ResponseEntity<>(errResp, HttpStatus.UNAUTHORIZED);
    }


}

