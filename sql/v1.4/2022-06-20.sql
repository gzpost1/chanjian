ALTER TABLE `tb_project_info` ADD COLUMN `is_top` tinyint(1) NULL DEFAULT 0 COMMENT '是否置顶 0:否, 1:是（默认否）' AFTER `latitude`;

ALTER TABLE `tb_project_info` ADD COLUMN `project_map` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '3d地图' AFTER `is_top`;

ALTER TABLE `tb_register_info` MODIFY COLUMN `data_permissions` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '数据统计权限（0正常 1停用）' AFTER `contact`;

UPDATE tb_register_info set data_permissions = 0;