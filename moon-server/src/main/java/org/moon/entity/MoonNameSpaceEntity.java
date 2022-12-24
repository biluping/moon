package org.moon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moon.entity.base.BaseEntity;

import java.io.Serializable;

/**
 * 名称空间表
 */
@TableName(value ="moon_name_space")
@Data
@EqualsAndHashCode(callSuper = true)
public class MoonNameSpaceEntity extends BaseEntity implements Serializable {

    /**
     * 应用id
     */
    private String appid;

    /**
     * 名称空间名称
     */
    private String name;
}