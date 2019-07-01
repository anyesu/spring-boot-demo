package com.github.anyesu.common.base;


import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author anyesu
 */
public interface BaseRepository<T> extends Neo4jRepository<T, Long> {
}
