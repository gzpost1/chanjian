ALTER TABLE `industry_monitoring_standard_wlcy`.`tb_project_info` ADD COLUMN `is_top` tinyint(1) NULL DEFAULT 0 COMMENT '是否置顶 0:否, 1:是（默认否）' AFTER `latitude`;

ALTER TABLE `industry_monitoring_standard_wlcy`.`tb_project_info` ADD COLUMN `project_map` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '3d地图' AFTER `is_top`;