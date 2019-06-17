package com.github.anyesu;

import com.github.anyesu.entity.User;
import com.github.anyesu.entity.UserDetail;
import com.github.anyesu.enums.Sex;
import com.github.anyesu.service.IUserService;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用户表测试类
 *
 * @author anyesu
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest extends BaseTest {

    @Resource
    private IUserService userService;

    /**
     * 测试新增用户
     */
    @Test
    public void testAdd() {
        log.info("测试新增用户");

        User query = new User();
        int count1 = userService.count(query);
        List<User> users = userService.select(query);
        logData("插入前数据：", users);

        UserDetail detail = UserDetail.builder().mobile("123xxxx1234").email("ooo@qq.com").build();
        User user = User.builder().name("testUser").sex(Sex.MALE).detail(detail).build();
        userService.insertSelective(user);

        int count2 = userService.count(query);
        users = userService.select(query);
        logData("插入后数据：", users);

        Assert.assertEquals(count1 + 1, count2);

        userService.insertSelective(new User());

        int count3 = userService.count(query);
        users = userService.select(query);
        logData("插入后数据：", users);

        Assert.assertEquals(count2 + 1, count3);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDelete() {
        log.info("测试删除用户");

        Integer id = 1;
        User user = userService.findById(id);
        logData("查询结果：", user);

        Assert.assertNotNull(user);

        // 删除存在的用户
        userService.deleteById(id);
        user = userService.findById(id);
        logData("查询结果：", user);

        Assert.assertNull(user);

        // 删除不存在的用户
        Assert.assertEquals(userService.deleteById(id), 0);
    }

    /**
     * 测试查找指定用户
     */
    @Test
    public void testFind() {
        log.info("测试查找指定用户");

        Integer id = 2;
        User user = userService.findById(id);
        logData("查询结果：", user);

        Assert.assertNotNull(user);

        id = -1;
        user = userService.findById(id);
        logData("查询结果：", user);

        Assert.assertNull(user);
    }

    /**
     * 测试查找所有用户
     */
    @Test
    public void testFindAll() {
        log.info("测试查找所有用户");

        List<User> users = userService.select(new User());
        logData("查询结果：", users);

        Assert.assertTrue(users.size() > 0);
    }

    /**
     * 测试修改用户
     */
    @Test
    public void testUpdate() {
        log.info("测试修改用户");

        Integer id = 3;
        User user = userService.findById(id);
        logData("修改前数据：", user);

        String name = "testUser";
        Sex sex = Sex.MALE;
        UserDetail detail = UserDetail.builder().mobile("123xxxx0000").email("email@qq.com").build();
        User modify = User.builder().name(name).sex(sex).detail(detail).build();
        modify.setId(id);
        userService.updateByPrimaryKey(modify);

        user = userService.findById(id);
        logData("修改后数据：", user);

        Assert.assertEquals(modify.toString(), user.toString());

        name = "testUser";
        modify = User.builder().name(name).build();
        modify.setId(id);
        userService.updateByPrimaryKey(modify);

        user = userService.findById(id);
        logData("修改后数据：", user);

        Assert.assertEquals(name, user.getName());
        Assert.assertNotEquals(modify.toString(), user.toString());
    }

}
