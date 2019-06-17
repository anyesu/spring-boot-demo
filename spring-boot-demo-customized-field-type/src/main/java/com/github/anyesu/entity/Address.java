package com.github.anyesu.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单地址
 *
 * @author anyesu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    /**
     * 手机
     */
    private String mobile;

    /**
     * 收货人姓名
     */
    private String receiver;

    /**
     * 详细地址
     */
    private String adr;

}
