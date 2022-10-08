/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - storydb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`storydb` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `storydb`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `a_id` varchar(20) NOT NULL,
  `shop_id` int DEFAULT NULL,
  `a_name` varchar(50) NOT NULL,
  `a_pwd` varchar(20) NOT NULL,
  `a_update` timestamp NOT NULL,
  PRIMARY KEY (`a_id`),
  KEY `FK_Relationship_11` (`shop_id`),
  CONSTRAINT `FK_Relationship_11` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_admin` */

insert  into `t_admin`(`a_id`,`shop_id`,`a_name`,`a_pwd`,`a_update`) values 
('admin',NULL,'超级管理员','admin','2022-05-18 10:47:23'),
('admin-1',1,'陕科大店管理员','admin','2022-05-18 10:47:23'),
('admin-2',2,'小寨华旗店管理员','admin','2022-05-18 10:47:23'),
('admin-3',3,'智链店管理员','admin','2022-05-18 10:47:23'),
('admin-4',4,'小寨皇家公馆店管理员','admin','2022-05-18 10:47:23'),
('admin-5',5,'未央大学城店管理员','admin','2022-05-18 10:47:23');

/*Table structure for table `t_cart` */

DROP TABLE IF EXISTS `t_cart`;

CREATE TABLE `t_cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `u_id` varchar(50) DEFAULT NULL,
  `shop_id` int DEFAULT NULL,
  `meal_id` int DEFAULT NULL,
  `cart_num` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `FK_Relationship_10` (`meal_id`),
  KEY `FK_Relationship_8` (`u_id`),
  KEY `FK_Relationship_9` (`shop_id`),
  CONSTRAINT `FK_Relationship_10` FOREIGN KEY (`meal_id`) REFERENCES `t_meal` (`meal_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_cart` */

insert  into `t_cart`(`cart_id`,`u_id`,`shop_id`,`meal_id`,`cart_num`) values 
(4,'u2',2,5,2),
(11,'u2',3,2,1),
(17,'u2',5,3,1),
(18,'u2',5,1,1),
(19,'u2',5,5,1),
(41,'user01',2,4,2),
(42,'user01',2,3,2),
(43,'user01',2,5,1),
(47,'user01',3,2,2),
(56,'2846579',1,2,1);

/*Table structure for table `t_cls` */

DROP TABLE IF EXISTS `t_cls`;

CREATE TABLE `t_cls` (
  `cls_id` int NOT NULL AUTO_INCREMENT,
  `cls_name` char(50) NOT NULL,
  PRIMARY KEY (`cls_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_cls` */

insert  into `t_cls`(`cls_id`,`cls_name`) values 
(1,'特色菜'),
(2,'主食'),
(3,'热菜'),
(4,'小吃'),
(5,'凉菜'),
(6,'汤羹'),
(7,'酒水');

/*Table structure for table `t_meal` */

DROP TABLE IF EXISTS `t_meal`;

CREATE TABLE `t_meal` (
  `meal_id` int NOT NULL AUTO_INCREMENT,
  `cls_id` int DEFAULT NULL,
  `meal_name` varchar(100) NOT NULL,
  `meal_price` decimal(10,2) NOT NULL,
  `meal_desc` varchar(500) NOT NULL,
  `meal_pic` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `insert_user` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`meal_id`),
  KEY `FK_Relationship_1` (`cls_id`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`cls_id`) REFERENCES `t_cls` (`cls_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_meal` */

insert  into `t_meal`(`meal_id`,`cls_id`,`meal_name`,`meal_price`,`meal_desc`,`meal_pic`,`insert_time`,`insert_user`) values 
(1,3,'红烧肉',28.00,'很好吃','pic1.jpg','2022-05-18 14:39:12',NULL),
(2,1,'辣子鸡',50.00,'好吃不腻','lzj.jpg','2022-05-18 22:36:01',NULL),
(3,3,'清蒸大虾',45.00,'好吃好看','0a271936447842b88418cf49e80431e6.jpg','2022-05-19 10:58:28',NULL),
(4,3,'扣肉夹馍',50.00,'美味佳肴','359999053b9b4c52abb08102b4e063e7.jpg','2022-05-19 11:09:36',NULL),
(5,2,'胡萝卜丝饺子',15.00,'纯天然无添加','c29b681e159d4a02ba154376ea8da148.jpg','2022-06-22 18:01:16',NULL),
(12,3,'麻辣鸭头',67.00,'又麻又辣又好吃','5b7ed295fda4465d98e26dc903c946b2.jpg','2022-08-14 21:43:54',NULL),
(13,7,'农夫山泉',3.00,'矿泉水','60e85e16b51947b69fd3a32449fbdeed.jpg','2022-08-14 22:29:36',NULL),
(15,7,'青岛啤酒',4.00,'清爽好喝','5da8270486564d3289f4bfaedb322971.jpg','2022-08-14 22:39:17',NULL),
(16,6,'西湖牛肉羹',20.00,'滋补好喝','f36c256c31b94deeb313e1afb62da8c5.jpg','2022-08-14 22:43:43',NULL),
(19,5,'素三拼',13.00,'特色凉拌菜','7d9304af697e4ab8a4f2b27ae88c2413.jpg','2022-09-27 10:16:02',NULL),
(20,7,'RIOR',10.00,'年轻人的最爱','11fe81067ece4cf886f3825870947461.jpg','2022-09-27 10:16:35',NULL);

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `shop_id` int DEFAULT NULL,
  `u_id` varchar(50) DEFAULT NULL,
  `order_amount` decimal(12,2) DEFAULT NULL,
  `order_submit` datetime DEFAULT NULL,
  `order_cancel` datetime DEFAULT NULL,
  `order_com` datetime DEFAULT NULL,
  `order_com_per` varchar(50) DEFAULT NULL,
  `order_status` int NOT NULL COMMENT '0 保存 1 已提交 2 已取消 3 已完成',
  PRIMARY KEY (`order_id`),
  KEY `FK_Relationship_4` (`u_id`),
  KEY `FK_Relationship_5` (`shop_id`),
  CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_order` */

insert  into `t_order`(`order_id`,`shop_id`,`u_id`,`order_amount`,`order_submit`,`order_cancel`,`order_com`,`order_com_per`,`order_status`) values 
(2,1,'user01',131.00,'2022-08-10 19:42:57',NULL,NULL,NULL,2),
(3,2,'user01',75.00,'2022-08-10 19:46:08',NULL,NULL,NULL,2),
(4,4,'u2',50.00,'2022-08-11 15:20:19',NULL,NULL,NULL,3),
(8,1,'u2',50.00,'2022-08-11 15:21:13',NULL,NULL,NULL,3),
(9,2,'user01',15.00,'2022-08-11 15:22:22',NULL,NULL,NULL,3),
(10,1,'u2',50.00,'2022-08-11 15:24:11',NULL,NULL,NULL,3),
(11,1,'u2',50.00,'2022-08-11 15:27:08',NULL,NULL,NULL,3),
(12,5,'user01',45.00,'2022-08-11 19:49:26',NULL,NULL,NULL,3),
(13,5,'user01',15.00,'2022-08-11 19:50:58',NULL,NULL,NULL,2),
(14,5,'user01',2112.00,'2022-08-11 20:27:42',NULL,NULL,NULL,2),
(15,1,'user01',69.00,'2022-08-11 20:32:55',NULL,NULL,NULL,3),
(16,2,'user01',95.00,'2022-08-11 20:36:21',NULL,NULL,NULL,3),
(18,2,'user01',15.00,'2022-09-29 12:15:40',NULL,NULL,NULL,3),
(25,3,'user01',145.00,'2022-09-29 14:52:14',NULL,NULL,NULL,-2),
(26,1,'user01',50.00,'2022-09-30 19:23:24',NULL,NULL,NULL,3),
(27,1,'user01',15.00,'2022-10-01 13:34:36',NULL,NULL,NULL,-3),
(28,4,'user01',25.00,'2022-10-01 13:37:55',NULL,NULL,NULL,3),
(29,4,'user01',28.00,'2022-10-07 23:57:49',NULL,NULL,NULL,2),
(30,1,'2846579',50.00,'2022-10-08 11:39:55',NULL,NULL,NULL,1);

/*Table structure for table `t_order_details` */

DROP TABLE IF EXISTS `t_order_details`;

CREATE TABLE `t_order_details` (
  `od_id` int NOT NULL AUTO_INCREMENT,
  `meal_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `od_num` int NOT NULL,
  PRIMARY KEY (`od_id`),
  KEY `FK_Relationship_6` (`order_id`),
  KEY `FK_Relationship_7` (`meal_id`),
  CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`meal_id`) REFERENCES `t_meal` (`meal_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_order_details` */

insert  into `t_order_details`(`od_id`,`meal_id`,`order_id`,`od_num`) values 
(2,1,2,3),
(4,5,3,5),
(5,2,4,1),
(6,4,8,1),
(7,5,9,1),
(8,4,10,1),
(9,4,11,1),
(10,3,12,1),
(11,5,13,1),
(12,1,14,11),
(13,5,14,22),
(14,3,14,33),
(15,1,15,2),
(16,5,15,1),
(18,4,16,1),
(19,3,16,1),
(20,5,18,1),
(39,2,25,1),
(40,3,25,1),
(41,4,25,1),
(42,2,26,1),
(43,5,27,1),
(44,5,28,1),
(45,20,28,1),
(46,1,29,1),
(47,4,30,1);

/*Table structure for table `t_shop` */

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `shop_id` int NOT NULL AUTO_INCREMENT,
  `shop_name` char(10) NOT NULL,
  `shop_addr` varchar(200) NOT NULL,
  `shop_open` varchar(50) DEFAULT NULL COMMENT '营业时间',
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_shop` */

insert  into `t_shop`(`shop_id`,`shop_name`,`shop_addr`,`shop_open`) values 
(1,'陕科大东门旗舰店','陕科大东门外','10:00 至 22:00'),
(2,'小寨华旗国际广场店','小寨华旗三楼','10:00 至 22:00'),
(3,'智链店','小寨赛格19楼','10:00 至 22:00'),
(4,'小寨皇家公馆店','小寨皇家公馆三楼','10:00 至 22:00'),
(5,'未央大学城店','未央大学城','10:00 至 22:00');

/*Table structure for table `t_shop_meal` */

DROP TABLE IF EXISTS `t_shop_meal`;

CREATE TABLE `t_shop_meal` (
  `sm_id` int NOT NULL AUTO_INCREMENT,
  `shop_id` int DEFAULT NULL,
  `meal_id` int DEFAULT NULL,
  `sm_store` int NOT NULL,
  `sm_sale` int NOT NULL,
  `sm_click` int NOT NULL,
  `sm_recommend` tinyint(1) NOT NULL,
  PRIMARY KEY (`sm_id`),
  KEY `FK_Relationship_2` (`shop_id`),
  KEY `FK_Relationship_3` (`meal_id`),
  CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`meal_id`) REFERENCES `t_meal` (`meal_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_shop_meal` */

insert  into `t_shop_meal`(`sm_id`,`shop_id`,`meal_id`,`sm_store`,`sm_sale`,`sm_click`,`sm_recommend`) values 
(4,2,5,100,3,36,1),
(5,2,4,100,1,0,1),
(6,2,3,100,1,0,0),
(7,3,4,97,3,34,0),
(8,3,2,99,1,3,1),
(9,3,3,99,1,0,0),
(10,4,4,78,0,0,0),
(11,4,2,21,0,0,1),
(12,4,1,29,1,1,0),
(13,5,3,46,0,0,1),
(14,5,4,22,0,0,0),
(20,1,2,33,1,1,1),
(26,1,5,20,1,1,0),
(29,1,4,75,1,1,1),
(33,1,1,45,1,0,1),
(34,5,1,12,0,0,1),
(35,5,5,45,0,0,1),
(37,1,20,0,0,1,0),
(38,1,3,0,0,1,0),
(39,1,16,0,0,2,0),
(40,1,13,0,0,7,0),
(41,3,13,0,0,0,0),
(42,3,15,0,0,0,0),
(43,3,19,0,0,0,0),
(44,4,20,88,1,1,0),
(45,4,16,23,0,0,0),
(46,4,5,55,1,1,0),
(50,5,15,0,0,0,0),
(51,5,13,0,0,0,0),
(52,5,2,0,0,0,0);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `u_id` varchar(50) NOT NULL,
  `u_pwd` varchar(20) NOT NULL,
  `u_nickname` varchar(50) DEFAULT NULL,
  `u_sex` varchar(10) DEFAULT NULL,
  `u_avatar` char(100) DEFAULT NULL,
  `u_register` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `t_user` */

insert  into `t_user`(`u_id`,`u_pwd`,`u_nickname`,`u_sex`,`u_avatar`,`u_register`) values 
('2846579','123456','litter_Li','男','559c8f76e2f4400ca31041ce4fcb0454.jpg','2022-10-07 21:23:27'),
('u2','123456','KKK_sys','男','d7665152e031454bbbb367ade6aecbe8.jpg','2022-08-09 16:16:31'),
('user01','123456','Youngest','女','bd99672827384de3bf2f95b798e26255.jpg','2022-08-06 14:19:07');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
