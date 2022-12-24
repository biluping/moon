package org.moon.config.advice;

import lombok.extern.slf4j.Slf4j;
import org.moon.entity.base.BaseVo;
import org.moon.exception.MoonBadRequestException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.moon.controller")
public class ExceptionAdvice {

    @ExceptionHandler(Throwable.class)
    public BaseVo<String> error(Throwable e) {
        log.error("全局异常捕获", e);
        return BaseVo.error(e.getMessage());
    }

    @ExceptionHandler(MoonBadRequestException.class)
    public BaseVo<String> badRequestExceptionHandler(Throwable e) {
        return BaseVo.error(e.getMessage());
    }
}
