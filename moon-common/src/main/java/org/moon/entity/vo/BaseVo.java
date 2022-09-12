package org.moon.entity.vo;

import lombok.Data;

@Data
public class BaseVo<T> {

    private Integer code;

    private String msg;

    private T data;

    private BaseVo(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseVo<String> ok(){
        return new BaseVo<>(200, "success", null);
    }

    public static <T> BaseVo<T> ok(T data){
        return new BaseVo<>(200,"success",data);
    }

    public static BaseVo<String> error(String msg){
        return new BaseVo<>(400,msg,null);
    }

}
