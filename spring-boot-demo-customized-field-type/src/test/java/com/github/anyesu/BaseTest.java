package com.github.anyesu;

import com.alibaba.fastjson.JSON;
import com.github.anyesu.project.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类基类
 *
 * @author anyesu
 */
@SpringBootTest(classes = Application.class)
public class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected void logData(String msg, Object data) {
        log.info("{}\n{}", msg, JSON.toJSONString(data, true));
    }
}
