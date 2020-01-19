/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : exam

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 19/01/2020 15:06:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` varchar(11) DEFAULT NULL COMMENT '序列',
  `p_id` int(11) DEFAULT NULL COMMENT '分级`',
  `name` varchar(50) NOT NULL COMMENT '课程',
  `priority` int(11) NOT NULL COMMENT '权重',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `created_at` bigint(20) NOT NULL COMMENT '创建时间',
  `updated_at` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` VALUES (23, '010000', 0, 'Java', 1, 1, 1574778259327, 1575879240928);
INSERT INTO `category` VALUES (24, '020000', 0, 'React', 1, 1, 1574778267719, 1576566806324);
INSERT INTO `category` VALUES (25, '010100', 23, 'For', 1, 1, 1574778278717, 1574778278717);
INSERT INTO `category` VALUES (26, '010101', 25, '循环', 1, 1, 1574778295871, 1575880946829);
INSERT INTO `category` VALUES (27, '020100', 24, 'Css', 1, 1, 1574778311814, 1574778311814);
INSERT INTO `category` VALUES (28, '020200', 24, 'html', 1, 1, 1574778320839, 1574778320839);
INSERT INTO `category` VALUES (29, '010200', 23, '集合', 1, 1, 1575879183612, 1575880952580);
INSERT INTO `category` VALUES (30, '010201', 29, 'map', 1, 1, 1575879207583, 1575879207583);
INSERT INTO `category` VALUES (31, '020101', 27, '样式', 1, 1, 1575879183612, 1575879183612);
INSERT INTO `category` VALUES (32, '020201', 28, '结构', 1, 1, 1575879183612, 1575879183612);
INSERT INTO `category` VALUES (33, '030000', 0, 'MySQL', 1, 2, 1577447561122, 1577447561122);
INSERT INTO `category` VALUES (34, '030100', 33, 'select', 1, 2, 1577447577590, 1577447577590);
INSERT INTO `category` VALUES (35, '030200', 33, 'delete', 1, 2, 1577447583691, 1577447583691);
INSERT INTO `category` VALUES (36, '030300', 33, 'update', 1, 2, 1577447590983, 1577447590983);
INSERT INTO `category` VALUES (37, '030400', 33, 'insert', 1, 2, 1577447617150, 1577447617150);
INSERT INTO `category` VALUES (38, '030401', 37, '增加', 1, 2, 1577447630455, 1577447630455);
INSERT INTO `category` VALUES (39, '030301', 36, '更新', 1, 2, 1577447641423, 1577447641423);
INSERT INTO `category` VALUES (40, '030201', 35, '删除', 1, 2, 1577447647542, 1577447647542);
INSERT INTO `category` VALUES (41, '030101', 34, '查询', 1, 2, 1577447655046, 1577447655046);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
