package org.macula.engine.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * <p>
 * <b>GenderEnum</b> 性别枚举
 * </p>
 */
public enum GenderEnum {
    MALE("M", "男"),  FEMALE("F", "女");

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private final String code;
    private String desc;


    @Override
    public String toString() {
        return "GenderEnum{" +
            "code='" + code + '\'' +
            ", desc='" + desc + '\'' +
            '}';
    }
}
