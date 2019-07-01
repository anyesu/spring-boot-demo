# spring-boot-demo-neo4j-spring-data-jpa

> **Spring Boot** 项目中集成 Neo4j ( Spring Data Jpa 方式 )   

## 使用
* 获取项目
```shell
git clone --depth=1 https://github.com/anyesu/spring-boot-demo
```

* 构建项目
```shell
cd spring-boot-demo/spring-boot-demo-neo4j/spring-boot-demo-neo4j-spring-data-jpa
mvn package -Dmaven.test.skip=true
```

* 启动项目
```shell
java -Xms128m -Xmx128m -Xmn64m -Xss256k -jar target/app.jar
```

* 使用下面命令验证结果 ( 也可以访问 Swagger: http://127.0.0.1:8080/doc.html )
```shell
# 添加测试数据
curl http://127.0.0.1:8080/neo4j/data
curl http://127.0.0.1:8080/neo4j/data
curl http://127.0.0.1:8080/neo4j/data

# 查询所有用户
curl http://127.0.0.1:8080/neo4j/users

# 分页查询用户
curl "http://127.0.0.1:8080/neo4j/users/page?pageNum=2&pageSize=2"

# 查询指定用户 ( 具体的 userId 从上一步中找 )
curl http://127.0.0.1:8080/neo4j/users/1

# 查询指定用户的所有权限
curl http://127.0.0.1:8080/neo4j/users/1/auths

# 新增用户
curl -H "Content-Type: application/json" -X POST http://127.0.0.1:8080/neo4j/users -d '
{
  "name": "测试用户-test",
  "roles": [
    {
      "code": "ROLE_USER_ADMIN",
      "name": "用户管理员-test",
      "auths": [
        {
          "code": "AUTH_USER_DELETE",
          "name": "删除用户-test"
        },
        {
          "code": "AUTH_USER_EDIT",
          "name": "编辑用户-test"
        },
        {
          "code": "AUTH_USER_ADD",
          "name": "新增用户-test"
        }
      ]
    },
    {
      "code": "ROLE_ORDER_ADMIN",
      "name": "订单管理员-test",
      "users": null,
      "auths": [
        {
          "code": "AUTH_ORDER_QUERY",
          "name": "查询订单-test"
        }
      ]
    }
  ]
}'

# 修改指定用户
curl -H "Content-Type: application/json" -X PATCH http://127.0.0.1:8080/neo4j/users/1 -d '
{
  "name": "测试用户-test2",
  "roles": [
    {
      "code": "ROLE_USER_ADMIN",
      "name": "用户管理员-test2",
      "auths": [
        {
          "code": "AUTH_USER_DELETE",
          "name": "删除用户-test2"
        }
      ]
    }
  ]
}'

# 删除指定用户
curl -H "Content-Type: application/json" -X DELETE http://127.0.0.1:8080/neo4j/users/1

# 删除全部用户
curl -H "Content-Type: application/json" -X DELETE http://127.0.0.1:8080/neo4j/users
```

## 文档
* [Spring Boot 集成 Neo4j](https://www.jianshu.com/p/7d29a4fac520)
