package com.github.anyesu.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 角色
 *
 * @author anyesu
 */
@Data
@NodeEntity(label = Role.TYPE)
@NoArgsConstructor
public class Role implements Serializable {

    public static final String TYPE = "Role";

    public Role(String code, String name) {
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

    /**
     * 所属的用户
     */
    @Relationship(type = "OWN", direction = Relationship.INCOMING)
    private List<User> users;

    /**
     * 拥有的权限
     */
    @Relationship(type = "HAS")
    private List<Auth> auths;

}
