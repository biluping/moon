package org.moon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moon.entity.base.BaseEntity;

import java.io.Serializable;

/**
 * 应用表
 */
@TableName(value ="moon_app")
@Data
@EqualsAndHashCode(callSuper = true)
public class MoonAppEntity extends BaseEntity implements Serializable {

    /**
     * 应用id
     */
    private String appid;

    /**
     * 应用url
     */
    private String describe;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}