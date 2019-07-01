package com.github.anyesu.controller;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.common.web.R;
import com.github.anyesu.entity.Neo4jModel;
import com.github.anyesu.service.INodeService;
import com.github.anyesu.service.IRelationService;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实例相关
 *
 * @author anyesu
 */
@RestController
@RequestMapping("neo4j/{type}")
public class NodeController {

    @Resource
    private INodeService nodeService;

    @Resource
    private IRelationService relationService;

    /**
     * 根据主键查询
     *
     * @param type 类型
     * @param id   主键
     * @return
     */
    @GetMapping("{id}")
    public R findOne(@PathVariable("type") String type, @PathVariable("id") Long id) {
        Neo4jModel query = nodeService.findByTypeAndId(type, id);
        return query == null ? R.error("该实例不存在") : R.success(query);
    }

    /**
     * 根据类型查找
     *
     * @param type 类型
     * @return
     */
    @GetMapping
    public R<List<Neo4jModel>> findAll(@PathVariable("type") String type) {
        List<Neo4jModel> list = nodeService.selectByType(type);
        return R.success(list);
    }

    /**
     * 根据类型分页查找
     *
     * @param type     类型
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("page")
    public R<PageInfo<Neo4jModel>> findPager(@PathVariable("type") String type, Integer pageNum, Integer pageSize) {
        Neo4jModel query = new Neo4jModel();
        PageInfo<Neo4jModel> result = nodeService.selectPageByType(type, query, pageNum, pageSize);
        return R.success(result);
    }

    /**
     * 新增
     *
     * @param type 类型
     * @param data 数据
     * @return
     */
    @PostMapping
    public R add(@PathVariable("type") String type, @RequestBody Neo4jModel entity) {
        return R.result(nodeService.insert(type, entity) > 0);
    }

    /**
     * 根据主键更新
     *
     * @param type   类型
     * @param id     主键
     * @param entity 数据
     * @return
     */
    @PatchMapping("{id}")
    public R update(@PathVariable("type") String type, @PathVariable("id") Long id, @RequestBody Neo4jModel entity) {
        Neo4jModel query = nodeService.findByTypeAndId(type, id);
        if (query == null) {
            return R.error("该实例不存在");
        }
        return R.result(nodeService.updateById(id, entity) > 0);
    }

    /**
     * 根据主键删除
     *
     * @param type 类型
     * @param id   主键
     * @return
     */
    @DeleteMapping("{id}")
    public R delete(@PathVariable("type") String type, @PathVariable("id") Long id) {
        Neo4jModel query = nodeService.findByTypeAndId(type, id);
        if (query == null) {
            return R.error("该实例不存在");
        }
        // 检查是否有关联, neo4j 删除节点前必须先删除关联
        int count = relationService.countByNodeId(id);
        if (count > 0) {
            return R.error("该实例存在关联关系，请先取消关联");
        }
        return R.result(nodeService.deleteById(id) > 0);
    }

    /**
     * 生成测试数据
     *
     * @return
     */
    @GetMapping("data")
    public R data(@PathVariable("type") String type) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(3, 10); i++) {
            nodeService.insert(type, randomData(type));
        }
        return R.success();
    }

    /**
     * 生成随机数据
     *
     * @return
     */
    private Neo4jModel randomData(String type) {
        Neo4jModel data = new Neo4jModel();
        data.put("name", type + System.currentTimeMillis());
        return data;
    }

}
