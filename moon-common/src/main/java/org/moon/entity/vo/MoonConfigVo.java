package org.moon.entity.vo;

import lombok.Data;

/**
 * 应用-配置表
 */
@Data
public class MoonConfigVo {

    /**
     * 应用id
     */
    private String appid;

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String value;

    /**
     * 是否发布
     */
    private Integer isPublish;

}