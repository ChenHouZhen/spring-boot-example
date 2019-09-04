package com.chenhz.blog.exception;

import com.chenhz.common.entity.R;
import com.chenhz.common.exception.RRExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends RRExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException e){
//        log.error(e.getMessage(), e);
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        List<Map<String,Object>> data = new ArrayList<>();

        for (ObjectError error:errorList) {
            System.out.println("---"+ error);
            Map<String,Object> map = new HashMap<>();
            map.put("msg",error.getDefaultMessage());
            map.put("field",((FieldError) error).getField());
            map.put("rejectedValue",((FieldError) error).getRejectedValue());
            data.add(map);
        }

        R r = new R();
        r.put("code", 400);
        r.put("msg", "参数错误");
        r.put("data",data);
        log.error(r.toString());
        return r;
    }

}
