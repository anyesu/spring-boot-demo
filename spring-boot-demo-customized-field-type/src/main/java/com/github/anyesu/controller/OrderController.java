package com.github.anyesu.controller;

import com.github.anyesu.common.base.RestServiceController;
import com.github.anyesu.entity.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 *
 * @author anyesu
 */
@RestController
@RequestMapping("orders")
public class OrderController extends RestServiceController<Order, Integer> {
}
