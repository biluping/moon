package org.moon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoonConfigPublishEnum {
    PUBLISH(1,"已发布"),
    UN_PUBLISH(0, "未发布");

    final int code;
    final String msg;
}
