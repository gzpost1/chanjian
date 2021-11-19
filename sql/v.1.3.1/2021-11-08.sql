ALTER TABLE tb_systemconfig_charts ADD h5_redirect_id bigint(20) NULL COMMENT 'H5跳转页面id';
ALTER TABLE tb_systemconfig_charts ADD h5_sample_img varchar(255) NULL COMMENT 'H5示例图';
ALTER TABLE tb_systemconfig_charts ADD h5_commponent_type varchar(50) NULL COMMENT 'H5组件类型';
ALTER TABLE tb_systemconfig_charts
  MODIFY COLUMN h5_commponent_type varchar(50) COMMENT 'H5组件类型' AFTER commponent_type,
  MODIFY COLUMN h5_sample_img varchar(255) COMMENT 'H5示例图' AFTER sample_img,
  MODIFY COLUMN index_item varchar(50) COMMENT '指标项' AFTER h5_redirect_id;


ALTER TABLE tb_systemconfig_architecture ADD type int DEFAULT 0 NULL COMMENT '0.大屏 1.h5';
ALTER TABLE tb_systemconfig_h5_menu ALTER COLUMN is_simulation SET DEFAULT 0;

ALTER TABLE tb_systemconfig_h5_menu MODIFY point_data json COMMENT 'point_data 点位展示数据';
ALTER TABLE tb_systemconfig_h5_menu MODIFY chart_data json COMMENT 'chart_data 图表数据';


INSERT INTO tb_systemconfig_architecture (menu_id, menu_name, parent_id, first_id, secon_id, three_id, page_id, is_show, is_simulation, sort_num, create_user, create_time, update_user, update_time, deleted, type) VALUES (643455252847034384, '首页', 1, 643455252847034384, null, null, null, 1, 0, 1, 1, '2021-11-11 14:21:27', -1, null, 0, 1);