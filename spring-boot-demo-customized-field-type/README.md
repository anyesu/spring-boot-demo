# spring-boot-demo-customized-field-type

> **Spring Boot** 项目中实体类自定义字段类型 ( 枚举 / JSON ) 的使用   

## 使用
* 获取项目
```shell
git clone --depth=1 https://github.com/anyesu/spring-boot-demo
```

* 构建并测试项目
```shell
cd spring-boot-demo/spring-boot-demo-customized-field-type
# 测试类: OrderTest UserTest
mvn package
```

* 启动项目
```shell
java -Xms32m -Xmx32m -Xmn16m -Xss256k -jar target/app.jar
```

* 访问
```shell
# curl http://127.0.0.1:8080/users
{
    "code":"0000",
    "data":[
        {
            "id":1,
            "name":"admin",
            "sex":0,
            "sexDesc":"未知"
        },
        {
            "id":2,
            "name":"root",
            "sex":1,
            "sexDesc":"男"
        },
        {
            "detail":{
                "email":"xxxx@qq.com",
                "mobile":"135xxxxxxxx"
            },
            "id":3,
            "name":"test",
            "sex":2,
            "sexDesc":"女"
        }
    ],
    "message":"成功",
    "success":true,
}
# curl http://127.0.0.1:8080/orders
{
    "code":"0000",
    "data":[
        {
            "address":{
                "adr":"yyy",
                "mobile":"135xxxxxxxx",
                "receiver":"xxx"
            },
            "id":1,
            "orderGoods":[
                {
                    "count":2,
                    "goodsName":"测试商品",
                    "goodsNo":"TEST001"
                }
            ],
            "orderNo":"201906160001",
            "status":1,
            "statusDesc":"待支付"
        },
        {
            "id":2,
            "orderGoods":[
                {
                    "count":2,
                    "goodsName":"测试商品",
                    "goodsNo":"TEST001"
                },
                {
                    "count":1,
                    "goodsName":"测试商品2",
                    "goodsNo":"TEST002"
                }
            ],
            "orderNo":"201906160002",
            "status":4,
            "statusDesc":"已收货"
        },
        {
            "id":3,
            "orderGoods":[
                {
                    "count":1,
                    "goodsName":"测试商品2",
                    "goodsNo":"TEST002"
                }
            ],
            "orderNo":"201906160003",
            "status":0,
            "statusDesc":"已取消"
        }
    ],
    "message":"成功",
    "success":true,
} 
```

## 文档
* [Spring Boot 实体类巧用枚举类型字段](https://www.jianshu.com/p/34212407037e)
* [Spring Boot 实体类巧用 JSON 类型字段](https://www.jianshu.com/p/7d24167ccf6d)