package com.github.anyesu.entity;

import com.github.anyesu.common.base.BaseEntity;
import com.github.anyesu.common.EnumValue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单表
 *
 * @author anyesu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity<Integer> {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private Status status;

    /**
     * 收货地址
     */
    private Address address;

    /**
     * 订单商品
     */
    private List<OrderGoods> orderGoods;

    /**
     * 序列化时显示状态描述
     *
     * @return
     */
    public String getStatusDesc() {
        return status == null ? null : status.desc();
    }

    /**
     * 订单状态枚举
     * <p>
     * 此内部类会被自动扫描到
     */
    @AllArgsConstructor
    public enum Status implements EnumValue {

        /**
         * 已取消
         */
        CANCEL((short) 0, "已取消"),

        /**
         * 待支付
         */
        WAIT_PAY((short) 1, "待支付"),

        /**
         * 待发货
         */
        WAIT_TRANSFER((short) 2, "待发货"),

        /**
         * 待收货
         */
        WAIT_RECEIPT((short) 3, "待收货"),

        /**
         * 已收货
         */
        RECEIVE((short) 4, "已收货"),

        /**
         * 已完结
         */
        COMPLETE((short) 5, "已完结");

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

}
