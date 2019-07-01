package com.github.anyesu.service.impl;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.common.base.BaseService;
import com.github.anyesu.entity.Auth;
import com.github.anyesu.entity.User;
import com.github.anyesu.repository.UserRepository;
import com.github.anyesu.service.IUserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author anyesu
 */
@Service
public class UserServiceImpl extends BaseService<User> implements IUserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Iterable<Auth> findUserAuths(Long id) {
        return userRepository.findUserAuths(id);
    }

    @Override
    public Iterable<Auth> findUserAuths2(Long id) {
        String cypher = "MATCH (n:`User`)-[:HAS]-(:`Role`)-[:HAS]-(m:`Auth`) WHERE ID(n) = { id } RETURN DISTINCT m";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        Result result = session.query(cypher, parameters);
        List<Auth> list = new ArrayList<>();
        // 会自动转换为已知的类型，如果未知则 i.get("n") 的返回类型为 NodeModel
        result.forEach(i -> list.add((Auth) i.get("m")));
        return list;
    }

    @Override
    public PageInfo<User> selectPage(User query, Integer pageNum, Integer pageSize) {
        String condition = String.format("MATCH (n:%s)", User.TYPE);
        return selectPage(condition, pageNum, pageSize);
    }

}
