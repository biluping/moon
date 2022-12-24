package org.moon.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoonConfigDto {
    private String key;
    private Object val;
    private Boolean isMoonConfig;
}
