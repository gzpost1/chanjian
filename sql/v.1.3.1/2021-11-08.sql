create table tb_systemconfig_h5_menu
(
  id            bigint auto_increment
  comment 'id'
    primary key,
  name          varchar(10)            null
  comment '页面名称',
  menu_type     varchar(50)            null
  comment '页面类型',
  is_showdate   tinyint(1)             null
  comment '0否 1是',
  sort_num      int                    null
  comment 'sort_num 展示序号',
  img_url       varchar(255)           null
  comment 'img_url 大屏缩略图',
  is_show       tinyint(1) default '1' null
  comment 'is_show 是否展示(0:否,1:是)',
  is_simulation tinyint(1) default '0' null
  comment 'is_simulation 是否启用模拟数据(0:否,1:是)',
  has_backdrop  tinyint(1)             null
  comment 'has_backdrop 是否启用背景板(0:否,1:是)',
  point_data    longtext               null
  comment 'point_data 点位展示数据',
  chart_data    longtext               null
  comment 'chart_data 图表数据',
  create_time   datetime               null
  comment 'create_time 创建时间',
  update_time   datetime               null
  comment 'update_time 更新时间',
  create_user   bigint                 null
  comment 'create_user 创建人id',
  update_user   bigint                 null
  comment 'update_user 更新人id',
  deleted       tinyint(1) default '0' not null
  comment 'deleted 是否删除：0-否,1-是',
  route_path    varchar(255)           null
  comment '前端路由',
  mapsize_type  tinyint(1)             null
  comment '1大 2小',
  iS_showreturn tinyint(1)             null
  comment '0否 1是'
)
  comment 'H5页面配置';



ALTER TABLE tb_systemconfig_charts ADD h5_redirect_id bigint(20) NULL COMMENT 'H5跳转页面id';
ALTER TABLE tb_systemconfig_charts ADD h5_sample_img varchar(255) NULL COMMENT 'H5示例图';
ALTER TABLE tb_systemconfig_charts ADD h5_commponent_type varchar(50) NULL COMMENT 'H5组件类型';
ALTER TABLE tb_systemconfig_charts
  MODIFY COLUMN h5_commponent_type varchar(50) COMMENT 'H5组件类型' AFTER commponent_type,
  MODIFY COLUMN h5_sample_img varchar(255) COMMENT 'H5示例图' AFTER sample_img,
  MODIFY COLUMN index_item varchar(50) COMMENT '指标项' AFTER h5_redirect_id;


ALTER TABLE tb_systemconfig_architecture ADD type int DEFAULT 0 NULL COMMENT '0.大屏 1.h5';


INSERT INTO tb_systemconfig_architecture (menu_id, menu_name, parent_id, first_id, secon_id, three_id, page_id, is_show, is_simulation, sort_num, create_user, create_time, update_user, update_time, deleted, type) VALUES (643455252847034384, '首页', 1, 643455252847034384, null, null, null, 1, 0, 1, 1, '2021-11-11 14:21:27', -1, null, 0, 1);


