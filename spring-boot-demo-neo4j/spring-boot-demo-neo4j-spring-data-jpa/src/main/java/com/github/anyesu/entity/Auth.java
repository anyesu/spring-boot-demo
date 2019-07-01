package com.github.anyesu.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 权限
 *
 * @author anyesu
 */
@Data
@NodeEntity(label = Auth.TYPE)
@NoArgsConstructor
public class Auth implements Serializable {

    public static final String TYPE = "Auth";

    public Auth(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 主键
     */
    @GraphId
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

}
