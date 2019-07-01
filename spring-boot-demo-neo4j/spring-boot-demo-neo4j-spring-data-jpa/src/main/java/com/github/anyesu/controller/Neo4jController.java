package com.github.anyesu.controller;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.common.web.R;
import com.github.anyesu.entity.Auth;
import com.github.anyesu.entity.Role;
import com.github.anyesu.entity.User;
import com.github.anyesu.service.IAuthService;
import com.github.anyesu.service.IRoleService;
import com.github.anyesu.service.IUserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Neo4j 测试
 *
 * @author anyesu
 */
@RestController
@RequestMapping("neo4j")
public class Neo4jController {

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IAuthService authService;

    /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping("users")
    public R findAllUser() {
        return R.success(userService.findAll(3));
    }

    /**
     * 分页查询用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("users/page")
    public R<PageInfo<User>> findPager(Integer pageNum, Integer pageSize) {
        User query = new User();
        PageInfo<User> result = userService.selectPage(query, pageNum, pageSize);
        return R.success(result);
    }

    /**
     * 查询指定用户
     *
     * @param id
     * @return
     */
    @GetMapping("users/{id}")
    public R findOneUser(@PathVariable("id") Long id) {
        User user = userService.findOne(id, 3);
        return user == null ? R.error("用户不存在") : R.success(user);
    }

    /**
     * 查询指定用户的所有权限
     *
     * @param id
     * @return
     */
    @GetMapping("users/{id}/auths")
    public R findUserAuths(@PathVariable("id") Long id) {
        if (userService.findOne(id) == null) {
            return R.error("用户不存在");
        }
        return R.success(userService.findUserAuths(id));
    }

    /**
     * 查询指定用户的所有权限
     *
     * @param id
     * @return
     */
    @GetMapping("users/{id}/auths2")
    public R findUserAuths2(@PathVariable("id") Long id) {
        if (userService.findOne(id) == null) {
            return R.error("用户不存在");
        }
        return R.success(userService.findUserAuths2(id));
    }

    /**
     * 新增用户
     *
     * @param id
     * @return
     */
    @PostMapping("users")
    public R addUser(@RequestBody User user) {
        user.setId(null);
        userService.save(user);
        user = userService.findOne(user.getId());
        return user == null ? R.error("失败") : R.success(user);
    }

    /**
     * 修改指定用户
     *
     * @param id
     * @return
     */
    @PatchMapping("users/{id}")
    public R updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        if (userService.findOne(id) == null) {
            return R.error("用户不存在");
        }
        user.setId(id);// 不指定id就会新增
        userService.save(user);
        return R.success(user);
    }

    /**
     * 删除指定用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("users/{id}")
    public R deleteUser(@PathVariable("id") Long id) {
        if (userService.findOne(id) == null) {
            return R.error("用户不存在");
        }
        userService.delete(id);
        return R.result(userService.findOne(id) == null);
    }

    /**
     * 删除全部用户
     *
     * @return
     */
    @DeleteMapping("users")
    public R deleteAllUser() {
        userService.deleteAll();
        return R.result(userService.count() == 0);
    }

    /**
     * 生成测试数据
     *
     * @return
     */
    @GetMapping("data")
    public R data() {
        List<Auth> list = randomOrderAuths();
        authService.save(list);

        Role orderAdminRole = new Role("ROLE_ORDER_ADMIN", "订单管理员");
        orderAdminRole.setAuths(list);
        roleService.save(orderAdminRole);

        list = randomUserAuths();
        // 如果此处不主动插入数据时，后面 roleService.save 会插入缺失数据 ( id 为空的 Auth 对象 )
        if (list.size() > 1) {
            // 新增成功会将id赋值给实体类
            authService.save(list.get(0));
        }

        Role userAdminRole = new Role("ROLE_USER_ADMIN", "用户管理员");
        userAdminRole.setAuths(list);
        roleService.save(userAdminRole);

        User user = new User("测试用户" + System.currentTimeMillis());
        user.setRoles(Arrays.asList(orderAdminRole, userAdminRole));
        userService.save(user);

        // fixme 正常情况下 findAll 的关联深度为1，但是在当前作用域下会包含上述 user 对象的数据 ( auths )
        return R.success(userService.findAll(3));
    }

    /**
     * 生成随机用户权限
     *
     * @return
     */
    private List<Auth> randomUserAuths() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Auth> list = new ArrayList<>();
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_USER_QUERY", "查询用户"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_USER_DELETE", "删除用户"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_USER_EDIT", "编辑用户"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_USER_ADD", "新增用户"));
        }
        return list;
    }

    /**
     * 生成随机订单权限
     *
     * @return
     */
    private List<Auth> randomOrderAuths() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Auth> list = new ArrayList<>();
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_ORDER_QUERY", "查询订单"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_ORDER_DELETE", "删除订单"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_ORDER_EDIT", "编辑订单"));
        }
        if (random.nextBoolean()) {
            list.add(new Auth("AUTH_ORDER_ADD", "新增订单"));
        }
        return list;
    }
}
