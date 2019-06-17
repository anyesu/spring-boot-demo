package com.github.anyesu.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 其他用户信息
 *
 * @author anyesu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail implements Serializable {

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;
}
