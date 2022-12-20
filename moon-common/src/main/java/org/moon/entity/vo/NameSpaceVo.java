package org.moon.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NameSpaceVo {

    // 应用自定义配置
    private String nameSpace;

    private List<MoonConfigVo> configList;
}
