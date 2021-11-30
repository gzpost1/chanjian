ALTER TABLE tb_systemconfig_architecture ADD four_id bigint(20) NULL COMMENT '第四级菜单';
ALTER TABLE tb_systemconfig_architecture
  MODIFY COLUMN four_id bigint(20) COMMENT '第四级菜单' AFTER three_id;