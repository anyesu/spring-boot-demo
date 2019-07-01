package com.github.anyesu.controller;

import com.github.anyesu.common.PageInfo;
import com.github.anyesu.common.web.R;
import com.github.anyesu.entity.Neo4jModel;
import com.github.anyesu.service.INodeService;
import com.github.anyesu.service.IRelationService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实例关联相关
 *
 * @author anyesu
 */
@RestController
@RequestMapping("neo4j/{type}/{id}/relations/{relation}/{dest}")
public class NodeRelationController {

    @Resource
    private INodeService nodeService;

    @Resource
    private IRelationService relationService;

    /**
     * 查询关联的实例
     *
     * @param type     源实例类型
     * @param id       源实例id
     * @param relation 关联类型
     * @param dest     目标实例类型
     * @return
     */
    @GetMapping
    public R<List<Neo4jModel>> findAll(@PathVariable("type") String type,
                                       @PathVariable("id") Long id,
                                       @PathVariable("relation") String relation,
                                       @PathVariable("dest") String dest) {
        List<Neo4jModel> list = relationService.selectNodeRelations(id, type, dest, relation);
        return R.success(list);
    }

    /**
     * 分页查询的实例
     *
     * @param type         源实例类型
     * @param id           源实例id
     * @param dest         目标实例类型
     * @param relation     关联类型
     * @param pageNum      页码
     * @param pageSize     每页大小
     * @param haveRelation 是否有关联，null 表示全部
     * @return
     */
    @GetMapping("page")
    public R findPager(@PathVariable("type") String type,
                       @PathVariable("id") Long id,
                       @PathVariable("relation") String relation,
                       @PathVariable("dest") String dest,
                       Integer pageNum,
                       Integer pageSize,
                       Boolean haveRelation) {
        Neo4jModel query = nodeService.findById(id);
        if (query == null) {
            return R.error("源实例不存在");
        }
        // 查询所有有对应关联关系的实例
        PageInfo<Neo4jModel> result = relationService
                .selectPageNodeRelations(id, type, dest, relation, haveRelation, pageNum, pageSize);
        return R.success(result);
    }

    /**
     * 关联实例
     *
     * @param type     源实例类型
     * @param id       源实例id
     * @param relation 关联类型
     * @param dest     目标实例类型
     * @param destId   目标实例id
     * @return
     */
    @PostMapping("{destId}")
    public R bindNode(@PathVariable("type") String type,
                      @PathVariable("id") Long id,
                      @PathVariable("relation") String relation,
                      @PathVariable("dest") String dest,
                      @PathVariable("destId") Long destId) {
        Neo4jModel query = nodeService.findById(id);
        if (query == null) {
            return R.error("源实例不存在");
        }
        query = nodeService.findById(destId);
        if (query == null) {
            return R.error("目标实例不存在");
        }
        return R.result(relationService.insert(id, destId, type, dest, relation) > 0);
    }

    /**
     * 取消关联实例
     *
     * @param type     源实例类型
     * @param id       源实例id
     * @param relation 关联类型
     * @param dest     目标实例类型
     * @param destId   目标实例id
     * @return
     */
    @DeleteMapping("{destId}")
    public R unbindNode(@PathVariable("type") String type,
                        @PathVariable("id") Long id,
                        @PathVariable("relation") String relation,
                        @PathVariable("dest") String dest,
                        @PathVariable("destId") Long destId) {
        Neo4jModel query = nodeService.findById(id);
        if (query == null) {
            return R.error("源实例不存在");
        }
        query = nodeService.findById(destId);
        if (query == null) {
            return R.error("目标实例不存在");
        }
        return R.result(relationService.delete(id, destId, type, dest, relation) > 0);
    }

}
