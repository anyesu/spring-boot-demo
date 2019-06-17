package com.github.anyesu.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单商品
 *
 * @author anyesu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoods implements Serializable {

    /**
     * 商品编码
     */
    private String goodsNo;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer count;

}
