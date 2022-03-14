-- auto-generated definition
create table tb_register_info
(
    id                   bigint               not null,
    company_name         varchar(255)         null comment '公司名称
',
    company_type         varchar(100)         null comment '企业类型',
    company_name_en      varchar(255)         null comment '公司英文名',
    phone                varchar(20)          null comment '联系人手机号',
    pwd                  varchar(255)         null comment '密码',
    address              varchar(999)         null comment '地址',
    email                varchar(100)         null comment '联系邮箱',
    longtitude           decimal(10, 6)       null comment '经度',
    latitude             decimal(10, 6)       null comment '纬度',
    label                json                 null comment '标签
',
    registered_capital   decimal(10, 2)       null comment '注册资本',
    registered_year      int                  null comment '注册年份',
    main_business        varchar(99)          null comment '主营业务',
    business_scope       text                 null comment '经营范围',
    investment_direction text                 null comment '投资方向',
    investment_project   text                 null comment '曾参与投资的项目',
    qualification_imgs   text                 null comment '资质',
    commercial_direction text                 null comment '业态方向',
    operation_direction  text                 null comment '运营方向 ',
    type                 int                  null comment '1.投资方 2.业态方 3.运营方',
    create_user          bigint               null,
    update_user          bigint               null,
    create_time          datetime             null,
    update_time          datetime             null,
    status               tinyint(1)           null,
    deleted              tinyint(1)           null,
    audit_status         tinyint(1) default 0 null comment '审核状态 0.待审核 1.通过 2.驳回',
    blacklist            tinyint(1) default 0 null comment '是否是黑名单',
    audit_time           datetime             null comment '审核时间',
    area_code            varchar(50)          null comment '所在地区域编码',
    contact              varchar(50)          null comment '联系人',
    constraint tb_register_info_id_uindex
        unique (id)
)
    comment '注册信息';

alter table tb_register_info
    add primary key (id);


-- auto-generated definition
create table tb_favorite
(
    id            bigint        null,
    company_id    bigint        null comment '注册企业的id',
    favorite_id   bigint        null comment '收藏id',
    type          int           null comment '1.企业数据 2.项目数据',
    create_time   datetime      null comment '收藏时间
',
    update_time   datetime      null,
    deleted       tinyint(1)    null,
    status        tinyint(1)    null,
    favorite_type int default 1 null comment '1.收藏 2.点赞'
)
    comment '企业的收藏';


-- auto-generated definition
create table tb_audit_info
(
    id            bigint    null,
    company_id    bigint    not null comment '企业id',
    audit_comment text      null comment '审核意见',
    create_user   bigint    null,
    update_user   bigint    null,
    create_time   datetime  null,
    update_time   datetime  null,
    audit_status  bigint(1) null comment '审核状态 0.待审核 1.通过 2.驳回'
)
    comment '审核记录';


INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (103, '账号权限类型', 'account_authority_type', '0', 1, '2022-03-09 10:09:49', -1, null, null);
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (104, '投资方标签', 'investment_label', '0', 1, '2022-03-09 16:14:26', 1, '2022-03-09 16:15:13', '投资方标签管理');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (105, '业态方标签', 'commercial_label', '0', 1, '2022-03-09 16:15:56', -1, null, '业态方标签管理');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (106, '运营方标签', 'business_label', '0', 1, '2022-03-09 16:16:40', -1, null, '运营方标签管理');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (107, '企业类型', 'company_type', '0', -1, '2022-03-09 17:21:22', 1, '2022-03-09 17:28:24', '企业类型');
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (108, '企业角色', 'company_role', '0', 1, '2022-03-09 17:30:47', -1, null, null);
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (109, '项目状态', 'project_status', '0', 1, '2022-03-11 11:21:12', -1, null, null);
INSERT INTO sys_dict_type (dict_id, dict_name, dict_type, status, create_user, create_time, update_user, update_time, remark) VALUES (110, '企业审核状态', 'company_verify_status', '0', 1, '2022-03-13 14:04:04', -1, null, null);

INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (782, 2, '驳回', '2', 'company_verify_status', null, null, 'N', '0', -1, '2022-03-14 10:37:51', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (781, 1, '通过', '1', 'company_verify_status', null, null, 'N', '0', -1, '2022-03-14 10:37:43', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (780, 0, '待审核', '0', 'company_verify_status', null, null, 'N', '0', -1, '2022-03-14 10:29:21', -1, '2022-03-14 10:37:33', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (779, 0, '运营方标签3', 'business_label', 'business_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (778, 0, '运营方标签2', 'business_label', 'business_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (777, 0, '运营方标签1', 'business_label', 'business_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (776, 0, '业态方标签3', 'commercial_label', 'commercial_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (775, 0, '业态方标签2', 'commercial_label', 'commercial_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (774, 0, '业态方标签1', 'commercial_label', 'commercial_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (773, 2, '业态方', '2', 'company_role', null, null, 'N', '0', 1, '2022-03-09 17:31:31', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (772, 3, '运营方', '3', 'company_role', null, null, 'N', '0', 1, '2022-03-09 17:31:19', -1, '2022-03-14 11:10:35', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (761, 1, '投资方', '1', 'company_role', null, null, 'N', '0', 1, '2022-03-09 17:31:07', -1, '2022-03-14 11:10:21', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (760, 8, '外商投资企业', '8', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:30:18', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (759, 7, '其他企业', '7', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:30:07', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (758, 6, '私营企业', '6', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:57', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (757, 5, '股份有限公司', '5', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:47', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (756, 4, '有限责任公司', '4', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:36', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (755, 3, '联营企业', '3', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:26', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (754, 2, '股份合作企业', '2', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:14', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (753, 1, '集体企业', '1', 'company_type', null, null, 'N', '0', 1, '2022-03-09 17:29:02', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (752, 0, '国有企业', '0', 'company_type', null, null, 'N', '0', -1, null, 1, '2022-03-09 17:28:51', null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (751, 0, '投资标签3', 'investment_label', 'investment_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (750, 0, '投资标签2', 'investment_label', 'investment_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (749, 0, '投资标签1', 'investment_label', 'investment_label', null, null, 'N', '0', -1, null, -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (748, 1, '企业权限', '1', 'account_authority_type', null, null, 'N', '0', 1, '2022-03-09 10:11:09', -1, null, null);
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_user, create_time, update_user, update_time, remark) VALUES (747, 0, '行政职能权限', '0', 'account_authority_type', null, null, 'N', '0', 1, '2022-03-09 10:10:56', -1, null, null);


