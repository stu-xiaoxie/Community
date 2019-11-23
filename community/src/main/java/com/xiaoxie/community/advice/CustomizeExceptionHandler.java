package com.xiaoxie.community.advice;


import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CancellationException;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){
        if (e instanceof CancellationException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务器已炸");
        }
        return new ModelAndView("error");
    }

}
