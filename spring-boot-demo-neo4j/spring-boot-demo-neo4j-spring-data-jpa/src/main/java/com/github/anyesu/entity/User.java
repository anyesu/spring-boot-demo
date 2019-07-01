package com.github.anyesu.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 用户
 *
 * @author anyesu
 */
@Data
@NodeEntity(label = User.TYPE)
@NoArgsConstructor
public class User implements Serializable {

    public static final String TYPE = "User";

    public User(String name) {
        this.name = name;
    }

    /**
     * 主键
     */
    @GraphId
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 拥有的角色
     */
    @Relationship(type = "HAS")
    private List<Role> roles;

    /**
     * 拥有的权限
     */
    private List<Auth> auths;

}
