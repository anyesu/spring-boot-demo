package com.github.anyesu.controller;

import com.github.anyesu.common.base.RestServiceController;
import com.github.anyesu.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author anyesu
 */
@RestController
@RequestMapping("users")
public class UserController extends RestServiceController<User, Integer> {
}
