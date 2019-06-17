package com.github.anyesu.common.base;

import java.io.Serializable;
import lombok.Data;

/**
 * 基础实体类
 *
 * @param <PK> 主键类型
 * @author anyesu
 */
@Data
public class BaseEntity<PK> implements Serializable {

    /**
     * 主键
     */
    private PK id;

}
