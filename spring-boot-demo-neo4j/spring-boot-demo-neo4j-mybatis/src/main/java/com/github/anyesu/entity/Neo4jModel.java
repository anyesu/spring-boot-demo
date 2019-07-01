package com.github.anyesu.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

/**
 * Neo4j 结果对象封装
 *
 * @author anyesu
 */
@NoArgsConstructor
public class Neo4jModel extends HashMap<String, Object> {

    public static final Neo4jModel EMPTY = new Neo4jModel(Collections.emptyMap());

    public static final String[] INTERNAL_KEYS = {"id", "labels"};

    public Neo4jModel(Map<String, Object> map) {
        super(map);
    }
}
