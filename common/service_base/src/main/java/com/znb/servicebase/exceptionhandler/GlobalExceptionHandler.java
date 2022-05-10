package com.znb.servicebase.exceptionhandler;

import com.znb.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理整个web controller的异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 指定异常类型
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("error");
    }

    /**
     * 指定异常类型
     * @param e
     * @return
     */
    @ExceptionHandler({ArithmeticException.class,NullPointerException.class})
    @ResponseBody
    public R handlerArithException(Exception e){
        e.printStackTrace();
        return R.error().message("ArithmeticException");
    }

    /**
     * 自定义异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler({GuliException.class})
    @ResponseBody
    public R error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
