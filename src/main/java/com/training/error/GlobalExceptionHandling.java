package com.training.error;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * author HuyLQ21
 */
@ControllerAdvice
public class GlobalExceptionHandling {

    /**
     * author longnb8
     * @todo: this method handle exception of project
     * @return exception page
     */
    @ExceptionHandler(Exception.class)
    private String  processException(Exception ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "exception-page";
    }


}
