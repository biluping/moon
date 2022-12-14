package org.moon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moon.entity.base.BaseEntity;

import java.io.Serializable;

/**
 * 应用-配置表
 */
@TableName(value ="moon_config")
@Data
@EqualsAndHashCode(callSuper = true)
public class MoonConfigEntity extends BaseEntity implements Serializable {

    /**
     * 应用id
     */
    private String appid;

    /**
     * 名称空间id
     */
    private Long nameSpaceId;

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
    private Boolean isPublish;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}