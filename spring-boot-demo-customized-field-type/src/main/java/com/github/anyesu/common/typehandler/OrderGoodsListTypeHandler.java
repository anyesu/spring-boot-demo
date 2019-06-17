package com.github.anyesu.common.typehandler;

import com.github.anyesu.common.typehandler.base.JsonTypeHandler;
import com.github.anyesu.entity.OrderGoods;
import java.util.List;

/**
 * 订单商品-类型转换器
 * <p>
 * 使用 Mybatis 自动扫描的方式注册
 *
 * @author anyesu
 */
public class OrderGoodsListTypeHandler extends JsonTypeHandler<List<OrderGoods>> {
}
