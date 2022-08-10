package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /*@ExceptionHandler(value = IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e){
        return "<h1>" + e.getMessage() + "</h1>";
    }*/

    // Exceptino이 부모이기에 자식을 다 받을 수 있음
    // 구체적으로 하면 그거를 실행함
     /*@ExceptionHandler(value = Exception.class)
    public String handleException(IllegalArgumentException e){
        return "<h1>" + e.getMessage() + "</h1>";
    }*/

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> handleException(IllegalArgumentException e){
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


}
