package org.moon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoonConfigDto {
    private String key;
    private String val;
    private Boolean isMoonConfig;
}
