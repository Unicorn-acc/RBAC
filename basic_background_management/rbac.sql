/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : rbac

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 28/05/2023 20:41:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `emp_no` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `dept_no` int NULL DEFAULT NULL COMMENT '部门id',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职务',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`emp_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (85, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:19');
INSERT INTO `emp` VALUES (86, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:20');
INSERT INTO `emp` VALUES (87, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:21');
INSERT INTO `emp` VALUES (88, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:24');
INSERT INTO `emp` VALUES (89, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:26');
INSERT INTO `emp` VALUES (90, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:27');
INSERT INTO `emp` VALUES (91, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:29');
INSERT INTO `emp` VALUES (92, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:30');
INSERT INTO `emp` VALUES (93, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:08:31');
INSERT INTO `emp` VALUES (94, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-27 21:56:57');
INSERT INTO `emp` VALUES (95, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-28 06:15:39');
INSERT INTO `emp` VALUES (96, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-28 06:15:42');
INSERT INTO `emp` VALUES (97, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-28 07:54:19');
INSERT INTO `emp` VALUES (98, 'aaa', 1, 'defaultjob', '12345678901', '2023-05-28 07:54:29');
INSERT INTO `emp` VALUES (99, 'defaultname', 1, 'defaultjob', '12345678901', '2023-05-28 08:49:59');
INSERT INTO `emp` VALUES (100, 'newname', 1, 'defaultjob', '12345678901', '2023-05-28 08:56:21');
INSERT INTO `emp` VALUES (101, 'newname2', 1, 'defaultjob', '12345678901', '2023-05-28 08:56:25');
INSERT INTO `emp` VALUES (102, 'hellonewname', 1, 'defaultjob', '12345678901', '2023-05-28 08:56:32');

-- ----------------------------
-- Table structure for emp_role
-- ----------------------------
DROP TABLE IF EXISTS `emp_role`;
CREATE TABLE `emp_role`  (
  `id` bigint NOT NULL,
  `emp_id` bigint NULL DEFAULT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp_role
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `pid` bigint NULL DEFAULT NULL,
  `order_num` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `menu_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
