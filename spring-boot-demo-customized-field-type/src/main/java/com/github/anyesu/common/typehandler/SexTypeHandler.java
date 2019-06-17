package com.github.anyesu.common.typehandler;

import com.github.anyesu.common.typehandler.base.EnumTypeHandler;
import com.github.anyesu.enums.Sex;

/**
 * 性别-类型转换器
 * <p>
 * 使用 Mybatis 自动扫描的方式注册
 *
 * @author anyesu
 */
public class SexTypeHandler extends EnumTypeHandler<Sex> {
}
