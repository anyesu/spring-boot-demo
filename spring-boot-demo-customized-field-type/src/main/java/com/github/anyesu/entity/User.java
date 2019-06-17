package com.github.anyesu.entity;

import com.github.anyesu.common.base.BaseEntity;
import com.github.anyesu.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 *
 * @author anyesu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Integer> {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Sex sex;

    /**
     * 其他用户信息
     */
    private UserDetail detail;

    /**
     * 序列化时显示性别描述
     *
     * @return
     */
    public String getSexDesc() {
        return sex == null ? null : sex.desc();
    }
}
