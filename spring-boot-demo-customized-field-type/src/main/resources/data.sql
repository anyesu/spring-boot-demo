INSERT INTO user (name, sex, detail) VALUES ('admin', '0', '');
INSERT INTO user (name, sex, detail) VALUES ('root', '1', '');
INSERT INTO user (name, sex, detail) VALUES ('test', '2', '{"mobile": "135xxxxxxxx", "email": "xxxx@qq.com"}');

INSERT INTO `order` (orderNo, status, address, orderGoods) VALUES ('201906160001', '1', '{"mobile": "123xxxxxxxx", "receiver": "xxx", "adr": "yyy"}', '[{"count": 2, "goodsNo": "TEST001", "goodsName": "测试商品"}]');
INSERT INTO `order` (orderNo, status, address, orderGoods) VALUES ('201906160002', '4', '', '[{"count": 2, "goodsNo": "TEST001", "goodsName": "测试商品"}, {"count": 1, "goodsNo": "TEST002", "goodsName": "测试商品2"}]');
INSERT INTO `order` (orderNo, status, address, orderGoods) VALUES ('201906160003', '0', '', '[{"count": 1, "goodsNo": "TEST002", "goodsName": "测试商品2"}]');
