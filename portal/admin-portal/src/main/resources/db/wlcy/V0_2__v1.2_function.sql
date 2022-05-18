SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `tb_project_info` ADD COLUMN `area_code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域编码' AFTER `view_num`;

ALTER TABLE `tb_project_info` ADD COLUMN `area_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域信息' AFTER `area_code`;

ALTER TABLE `tb_project_info` ADD COLUMN `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址' AFTER `area_name`;

ALTER TABLE `tb_project_info` ADD COLUMN `longitude` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度' AFTER `address`;

ALTER TABLE `tb_project_info` ADD COLUMN `latitude` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度' AFTER `longitude`;

ALTER TABLE `tb_project_info` ADD INDEX `index_area_code`(`area_code`) USING BTREE COMMENT '普通索引-区域编码';

-- ----------------------------
-- Table structure for tb_project_label
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_label`;
CREATE TABLE `tb_project_label`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除, 0:否, 1:是',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态（0-停用 1-启用）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_status`(`status`) USING BTREE COMMENT '普通索引-状态',
  INDEX `index_deleted`(`deleted`) USING BTREE COMMENT '普通索引-删除标识'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_project_label_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_label_relation`;
CREATE TABLE `tb_project_label_relation`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `project_id` bigint(20) NULL DEFAULT NULL COMMENT '项目id',
  `label_id` bigint(20) NULL DEFAULT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_project_id`(`project_id`) USING BTREE COMMENT '普通索引-项目id',
  INDEX `index_label_id`(`label_id`) USING BTREE COMMENT '普通索引-标签id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目-标签关系' ROW_FORMAT = Dynamic;




SET FOREIGN_KEY_CHECKS = 1;
