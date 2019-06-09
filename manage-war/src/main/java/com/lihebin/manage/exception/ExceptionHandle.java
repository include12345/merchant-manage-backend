package com.lihebin.manage.exception;

import com.lihebin.manage.bean.Code;
import com.lihebin.manage.bean.Result;
import com.lihebin.manage.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * Created by lihebin on 2019/5/22.
 */
@ControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)  //申明捕获那个异常类
    @ResponseBody  //返回给浏览器的是一个json格式，上面又没有@RestController，所以在此申明@ResponseBody
    public Result handle(Exception e) {
        if (e instanceof BackendException) {
            BackendException backendException = (BackendException) e;
            return ResultUtil.error(backendException.getCode(), backendException.getMessage());
        } else if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            return ResultUtil.error(Code.CODE_PARAM_ERROR, e.getMessage());
        }
        logger.error("【系统异常】", e);
        return ResultUtil.error(Code.CODE_SYSTEM_ERROR, "系统异常");
    }
}
