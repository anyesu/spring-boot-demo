package com.github.anyesu;

import com.github.anyesu.entity.Address;
import com.github.anyesu.entity.Order;
import com.github.anyesu.entity.Order.Status;
import com.github.anyesu.entity.OrderGoods;
import com.github.anyesu.service.IOrderService;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 订单表测试类
 *
 * @author anyesu
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderTest extends BaseTest {

    @Resource
    private IOrderService orderService;

    /**
     * 测试新增订单
     */
    @Test
    public void testAdd() {
        log.info("测试新增订单");

        Order query = new Order();
        int count1 = orderService.count(query);
        List<Order> orders = orderService.select(query);
        logData("插入前数据：", orders);

        String orderNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0001";
        Status status = Status.WAIT_PAY;
        Address address = Address.builder().mobile("123xxxx0000").receiver("yyy").adr("zzz").build();
        OrderGoods orderGoods = OrderGoods.builder().goodsNo("TEST-001").goodsName("测试商品001").count(2).build();
        List<OrderGoods> orderGoodsList = Collections.singletonList(orderGoods);
        Order order = Order.builder().orderNo(orderNo).status(status)
                           .address(address).orderGoods(orderGoodsList)
                           .build();
        orderService.insertSelective(order);

        int count2 = orderService.count(query);
        orders = orderService.select(query);
        logData("插入后数据：", orders);

        Assert.assertEquals(count1 + 1, count2);

        try {
            orderService.insert(new Order());
        } catch (Exception e) {
            log.error("插入出错", e);
        }

        int count3 = orderService.count(query);
        orders = orderService.select(query);
        logData("插入后数据：", orders);

        // h2 数据库 not null 约束不生效, mysql 下结果应该是 count2 = count3
        Assert.assertEquals(count2 + 1, count3);
    }

    /**
     * 测试删除订单
     */
    @Test
    public void testDelete() {
        log.info("测试删除订单");

        Integer id = 2;
        Order order = orderService.findById(id);
        logData("查询结果：", order);

        Assert.assertNotNull(order);

        // 删除存在的订单
        orderService.deleteById(id);
        order = orderService.findById(id);
        logData("查询结果：", order);

        Assert.assertNull(order);

        // 删除不存在的订单
        Assert.assertEquals(orderService.deleteById(id), 0);
    }

    /**
     * 测试查找指定订单
     */
    @Test
    public void testFind() {
        log.info("测试查找指定订单");

        Integer id = 1;
        Order order = orderService.findById(id);
        logData("查询结果：", order);

        Assert.assertNotNull(order);

        id = -1;
        order = orderService.findById(id);
        logData("查询结果：", order);

        Assert.assertNull(order);
    }

    /**
     * 测试查找所有订单
     */
    @Test
    public void testFindAll() {
        log.info("测试查找所有订单");

        List<Order> orders = orderService.select(new Order());
        logData("查询结果：", orders);

        Assert.assertTrue(orders.size() > 0);
    }

    /**
     * 测试修改订单
     */
    @Test
    public void testUpdate() {
        log.info("测试修改订单");

        Integer id = 3;
        Order order = orderService.findById(id);
        logData("修改前数据：", order);

        String orderNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-1001";
        Status status = Status.WAIT_RECEIPT;
        Address address = Address.builder().mobile("123xxxx0000").receiver("yyy").adr("zzz").build();
        OrderGoods orderGoods = OrderGoods.builder().goodsNo("TEST-002").goodsName("测试商品002").count(3).build();
        List<OrderGoods> orderGoodsList = Collections.singletonList(orderGoods);
        Order modify = Order.builder().orderNo(orderNo).status(status)
                            .address(address).orderGoods(orderGoodsList)
                            .build();
        modify.setId(id);
        orderService.updateByPrimaryKey(modify);

        order = orderService.findById(id);
        logData("修改后数据：", order);

        Assert.assertEquals(modify.toString(), order.toString());

        orderNo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-1002";
        modify = Order.builder().orderNo(orderNo).build();
        modify.setId(id);
        orderService.updateByPrimaryKeySelective(modify);

        order = orderService.findById(id);
        logData("修改后数据：", order);

        Assert.assertEquals(orderNo, order.getOrderNo());
        Assert.assertNotEquals(modify.toString(), order.toString());
    }

}
