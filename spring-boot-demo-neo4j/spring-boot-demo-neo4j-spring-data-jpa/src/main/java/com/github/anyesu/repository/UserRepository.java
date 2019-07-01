package com.github.anyesu.repository;

import com.github.anyesu.common.base.BaseRepository;
import com.github.anyesu.entity.Auth;
import com.github.anyesu.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;


/**
 * 用户
 *
 * @author anyesu
 */
public interface UserRepository extends BaseRepository<User> {

    /**
     * 查询用户的所有权限
     *
     * @param id 用户id
     * @return
     */
    @Query("MATCH (n:`User`)-[:HAS]-(:`Role`)-[:HAS]-(m:`Auth`) WHERE ID(n) = { id } RETURN DISTINCT m")
    Iterable<Auth> findUserAuths(@Param("id") Long id);

}
