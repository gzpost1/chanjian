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

