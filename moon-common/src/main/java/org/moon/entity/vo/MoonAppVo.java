package org.moon.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MoonAppVo {

    /**
     * 应用id
     */
    private String appid;

    /**
     * 应用url
     */
    private String describe;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}