package org.moon.exception;

/**
 * Moon 请求异常
 */
public class MoonRequestException extends RuntimeException{

    public MoonRequestException(String msg){
        super(msg);
    }
}
