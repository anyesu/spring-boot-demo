package com.github.anyesu.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 响应报文结构
 *
 * @author anyesu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("WeakerAccess")
public class R<T> {

    private static final String DEFAULT_SUCCESS_CODE = "0000";

    private static final String DEFAULT_SUCCESS_MSG = "成功";

    private static final String DEFAULT_ERROR_CODE = "9999";

    private static final String DEFAULT_ERROR_MSG = "失败";

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态码: 0000-成功; 9999-失败
     */
    private String code;

    /**
     * 成功/错误信息
     */
    private String message;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 响应时间
     */
    private Long timestamp;

    /**
     * 请求ID
     */
    private String requestId;

    public static R result(Boolean success) {
        return Boolean.TRUE.equals(success) ? success() : error();
    }

    // ======================== 成功 ========================

    public static R success() {
        return success(null);
    }

    public static <T> R<T> success(T data) {
        return success(DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> R<T> success(String message, T data) {
        return success(DEFAULT_SUCCESS_CODE, message, data);
    }

    public static <T> R<T> success(String code, String message, T data) {
        return new R<T>(true, code, message, data, System.currentTimeMillis(), null);
    }

    // ======================== 成功 ========================

    // ======================== 失败 ========================

    public static R error() {
        return error(DEFAULT_ERROR_MSG);
    }

    public static R error(String message) {
        return error(message, null);
    }

    public static <T> R<T> error(String message, T data) {
        return error(DEFAULT_ERROR_CODE, message, data);
    }

    public static <T> R<T> error(String code, String message, T data) {
        return new R<T>(false, code, message, data, System.currentTimeMillis(), null);
    }

    // ======================== 失败 ========================
}
