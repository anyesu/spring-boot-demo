DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(40) NOT NULL DEFAULT '' COMMENT '姓名',
  sex tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别',
  detail longtext NOT NULL COMMENT '其他用户信息',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  orderNo varchar(40) NOT NULL COMMENT '订单号',
  status tinyint(1) NOT NULL COMMENT '订单状态',
  address longtext NOT NULL COMMENT '收货地址',
  -- mysql 要 5.7+ 支持 json 类型
  -- orderGoods json NOT NULL COMMENT '订单商品',
  -- h2 不支持 json 类型, 使用 longtext 类型代替
  orderGoods longtext NOT NULL COMMENT '订单商品',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
