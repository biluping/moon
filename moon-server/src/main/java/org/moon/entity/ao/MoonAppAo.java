package org.moon.entity.ao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoonAppAo {

    /**
     * 应用id
     */
    private String appid;

    /**
     * 应用url
     */
    private String appUrl;

}