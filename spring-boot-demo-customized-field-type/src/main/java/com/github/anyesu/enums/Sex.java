package com.github.anyesu.enums;

import com.github.anyesu.common.EnumValue;
import lombok.AllArgsConstructor;

/**
 * 性别
 *
 * @author anyesu
 */
@AllArgsConstructor
public enum Sex implements EnumValue {

    /**
     * 男
     */
    MALE((short) 1, "男"),

    /**
     * 女
     */
    FEMALE((short) 2, "女"),

    /**
     * 未知
     */
    UNKNOWN((short) 0, "未知");

    private final Short value;

    private final String desc;

    public Short value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    @Override
    public Object toValue() {
        return value;
    }

}
