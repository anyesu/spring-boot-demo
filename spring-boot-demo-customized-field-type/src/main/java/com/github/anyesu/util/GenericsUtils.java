package com.github.anyesu.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型工具类
 *
 * @author anyesu
 */
@SuppressWarnings("WeakerAccess")
public class GenericsUtils {

    /**
     * 通过反射,获得指定类的父类的第一个泛型参数的实际类型.
     * 如 class Test extends HashMap<String, Object> {
     *
     * @param clazz 需要反射的类,该类必须继承泛型父类
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Class getSuperClassGenericClass(Class clazz) {
        return getSuperClassGenericClass(clazz, 0);
    }

    /**
     * 通过反射,获得指定类的父类的第 index 个泛型参数的实际类型.
     * 如 class Test extends HashMap<String, Object> {
     *
     * @param clazz 需要反射的类,该类必须继承范型父类
     * @param index 泛型参数所在索引, 从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Class getSuperClassGenericClass(Class clazz, int index) {
        Type type = getSuperClassGenericType(clazz, index);
        return type instanceof Class ? (Class) type : Object.class;
    }

    /**
     * 通过反射,获得指定类的父类的第一个泛型参数的实际类型.
     * 如 class Test extends HashMap<String, Object> {
     *
     * @param clazz 需要反射的类,该类必须继承泛型父类
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Type getSuperClassGenericType(Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    /**
     * 通过反射,获得指定类的父类的第 index 个泛型参数的实际类型.
     * 如 class Test extends HashMap<String, Object> {
     *
     * @param clazz 需要反射的类,该类必须继承范型父类
     * @param index 泛型参数所在索引, 从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Type getSuperClassGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();// 得到泛型父类
        return getGenericType(genType, index);
    }

    /**
     * 通过反射,获得方法返回值第一个泛型参数的实际类型.
     * 如: public Map<String, Object> getNames(){}
     *
     * @param method 方法
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Type getMethodGenericReturnType(Method method) {
        return getMethodGenericReturnType(method, 0);
    }

    /**
     * 通过反射,获得方法返回值第 index 个泛型参数的实际类型.
     * 如: public Map<String, Object> getNames(){}
     *
     * @param method 方法
     * @param index  泛型参数所在索引, 从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回 <code>Object.class</code>
     */
    public static Type getMethodGenericReturnType(Method method, int index) {
        Type returnType = method.getGenericReturnType();
        return getGenericType(returnType, index);
    }

    /**
     * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型.
     * 如: void test(HashMap<Object, BigDecimal> map, List<String> list) {}
     *
     * @param method 方法
     * @return 输入参数的泛型参数的实际类型集合, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回空集合
     */
    public static List<Type> getMethodGenericParameterTypes(Method method) {
        return getMethodGenericParameterTypes(method, 0);
    }

    /**
     * 通过反射,获得方法输入参数第 index 个输入参数的所有泛型参数的实际类型.
     * 如: void test(HashMap<Object, BigDecimal> map, List<String> list) {}
     *
     * @param method 方法
     * @param index  第几个输入参数
     * @return 输入参数的泛型参数的实际类型集合, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回空集合
     */
    public static List<Type> getMethodGenericParameterTypes(Method method, int index) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();

        if (index >= genericParameterTypes.length || index < 0) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
        }

        List<Type> results = new ArrayList<>();
        Type type = genericParameterTypes[index];

        if (type instanceof ParameterizedType) {
            Type[] parameterArgTypes = getGenericTypes(type);
            results.addAll(Arrays.asList(parameterArgTypes));
        }

        return results;
    }

    /**
     * 通过反射,获得字段第一个泛型参数的实际类型.
     * 如: public Map<String, Object> names;
     *
     * @param field 字段
     * @param index 泛型参数所在索引,从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回<code>Object.class</code>
     */
    public static Type getFieldGenericType(Field field) {
        return getFieldGenericType(field, 0);
    }

    /**
     * 通过反射,获得字段第 index 个泛型参数的实际类型.
     * 如: public Map<String, Object> names;
     *
     * @param Field field 字段
     * @param int   index 泛型参数所在索引,从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回<code>Object.class</code>
     */
    public static Type getFieldGenericType(Field field, int index) {
        Type genericFieldType = field.getGenericType();
        return getGenericType(genericFieldType, index);
    }

    /**
     * 获得指定类的第 index 个泛型参数的实际类型.
     * 如 Map<String, Object>
     *
     * @param type  泛型类型
     * @param index 泛型参数所在索引, 从0开始.
     * @return 泛型参数的实际类型, 如果没有实现 {@link ParameterizedType} 接口, 即不支持泛型, 所以直接返回<code>Object.class</code>
     */
    public static Type getGenericType(Type type, int index) {
        // 如果没有实现 ParameterizedType 接口, 即不支持泛型, 直接返回 Object.class
        if (!(type instanceof ParameterizedType)) {
            return Object.class;
        }

        // 返回表示此类型实际类型参数的 Type 对象的数组,数组里放的都是对应类型的Class,
        // 如 Map<String, Object> 就返回 [String.class, Object.class]
        Type[] typeArguments = getGenericTypes(type);
        if (index >= typeArguments.length || index < 0) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
        }

        return typeArguments[index];
    }

    /**
     * 获得指定泛型类的所有泛型参数.
     * 如 Map<String, Object>
     *
     * @param type 泛型类型
     * @return
     */
    public static Type[] getGenericTypes(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return new Type[0];
        }

        ParameterizedType pType = (ParameterizedType) type;
        return pType.getActualTypeArguments();
    }
}
