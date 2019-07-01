# spring-boot-demo-neo4j-mybatis

> **Spring Boot** 项目中集成 Neo4j ( Mybatis 方式 )   

## 使用
* 使用 Docker 启动 Neo4j 数据库
```shell
# http://127.0.0.1:7474 bolt://127.0.0.1:7687
# 通过浏览器访问管理界面，登录后修改数据库密码为 neo4j123456
docker run -d --name neo4j -p 7474:7474 -p 7687:7687 -v `pwd`/neo4j/data:/data neo4j
```

* 获取项目
```shell
git clone --depth=1 https://github.com/anyesu/spring-boot-demo
```

* 构建项目
```shell
cd spring-boot-demo/spring-boot-demo-neo4j/spring-boot-demo-neo4j-mybatis
mvn package -Dmaven.test.skip=true
```

* 启动项目
```shell
java -Xms32m -Xmx32m -Xmn16m -Xss256k -jar target/app.jar
```

* 使用下面命令验证结果 ( 也可以访问 Swagger: http://127.0.0.1:8080/doc.html )
```shell
# 添加测试数据
curl http://127.0.0.1:8080/neo4j/user/data

# 查询所有用户
curl http://127.0.0.1:8080/neo4j/user

# 分页查询用户
curl "http://127.0.0.1:8080/neo4j/user/page?pageNum=2&pageSize=2"

# 查询指定用户 ( 具体的 userId 从上一步中找 )
curl http://127.0.0.1:8080/neo4j/user/1

# 新增用户
curl -H "Content-Type: application/json" -X POST http://127.0.0.1:8080/neo4j/user -d '
{
  "name": "测试用户-test"
}'

# 修改指定用户 ( labels 应该是无法被修改的 )
curl -H "Content-Type: application/json" -X PATCH http://127.0.0.1:8080/neo4j/user/1 -d '
{
  "name": "测试用户-test2",
  "labels": [
    "user1"
  ]
}'

# 删除指定用户
curl -H "Content-Type: application/json" -X DELETE http://127.0.0.1:8080/neo4j/user/1

# 关联用户1和用户2
curl -H "Content-Type: application/json" -X POST http://127.0.0.1:8080/neo4j/user/1/relations/child_of/user/2

# 查询用户1关联的实例
curl "http://127.0.0.1:8080/neo4j/user/1/relations/child_of/user"

# 分页查询用户1关联的实例
curl "http://127.0.0.1:8080/neo4j/user/1/relations/child_of/user/page?pageNum=1&pageSize=2&haveRelation=true"

# 取消关联用户1和用户2
curl -H "Content-Type: application/json" -X DELETE http://127.0.0.1:8080/neo4j/user/1/relations/child_of/user/2
```

## 文档
* [Spring Boot 集成 Neo4j](https://www.jianshu.com/p/7d29a4fac520)
