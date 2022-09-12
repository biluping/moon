package org.moon.entity.base;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {

    @TableId
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
