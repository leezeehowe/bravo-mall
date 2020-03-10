/**
  角色表
 */
create table role
(
    id          bigint auto_increment primary key comment '主键，自增',
    par_id      bigint not null comment '上级角色id，0->一级角色',
    level       int not null comment '所在角色树层次，0->一级角色，1->二级角色',
    name        varchar(64) not null comment '角色名称',
    status      int(1) default 0 comment '角色状态，0->有效的，1->无效的',
    description varchar(200) comment '角色描述',
    create_by   bigint not null comment '创建该角色的账号id，0->系统初始化',
    update_by   bigint not null comment '修改该角色的账号id，0->系统初始化',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '角色表';

/**
  接口资源表
 */
create table api_resource
(
    id          bigint auto_increment primary key comment '主键，自增',
    url         varchar(64) not null comment '资源url路径',
    name        varchar(64) not null unique comment '资源名',
    status      int(1) default 0 comment '接口状态，0->可用，1->不可用',
    belong_to   varchar(64) not null  comment '资源所属服务名',
    description varchar(200) comment '资源描述',
    create_by   bigint not null comment '创建该资源的账户id, 0->系统初始化',
    update_by   bigint not null comment '修改该角色的账号id，0->系统初始化',
    version     varchar(10) default '0.0.1' comment '资源版本',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '接口资源表';

/**
  页面资源表
 */
create table webpage_resource
(
    id          bigint auto_increment primary key comment '主键，自增',
    par_id      varchar(64) not null comment '上级id，0->一级菜单',
    name        varchar(64) not null COMMENT '资源名',
    text        varchar(64) comment '页面文字内容',
    url         varchar(64) COMMENT 'URL',
    icon        varchar(64) COMMENT '图标',
    order_num   int default 1 COMMENT '排序',
    type        int not null comment '类型，0->菜单，1->菜单项，2->超链接，3->按钮',
    description varchar(200) comment '资源描述',
    create_by   bigint not null comment '创建页面资源的账户id，0->系统初始化',
    update_by   bigint not null comment '修改该页面资源的账号id，0->系统初始化',
    version     varchar(10) default '0.0.1' comment '资源版本',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '网页资源表';

/**
  权限表：角色-资源映射表
 */
create table authority
(
    id            bigint auto_increment primary key comment '主键，自增',
    role_id       bigint not null comment '角色id',
    resource_id   bigint not null comment '资源id，只能是父级角色拥有的资源id',
    resource_type int not null comment '资源类型，0->接口资源，1->页面资源',
    type          int not null comment '类型，0->拥有该权限，1->无该权限',
    create_by     bigint not null comment '创建该权限的账户id，0->系统初始化',
    update_by     bigint not null comment '修改该权限的账户id，0->系统初始化',
    description   varchar(200) comment '资源描述',

    create_time   datetime default current_timestamp comment '创建时间',
    update_time   datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '权限表，由角色和资源之间的映射';

/**
  角色颁发表：用户-角色映射表
 */
create table role_issue
(
    id          bigint auto_increment primary key comment '主键，自增',
    user_id     bigint not null comment '用户id',
    role_id     bigint not null comment '角色id',
    status      int(1) default 0 comment '状态，0->有效，1->无效',
    create_by   bigint not null comment '创建该颁发记录的账户id，0->系统初始化',
    update_by   bigint not null comment '修改该颁发记录的账户id，0->系统初始化',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '角色颁发表，即用户与角色之间的映射';


/**
  用户表：服务内部的用户表，为了解耦。
 */
create table user
(
    id          bigint auto_increment primary key comment '主键，自增',
    external_id   char(32) not null comment '用户中心的用户账号uuid',
    status      int(1) default 0 comment '状态，0->有效，1->无效',
    create_by   bigint not null comment '创建该颁发记录的账户id，0->系统自动操作',
    update_by   bigint not null comment '修改该颁发记录的账户id，0->系统自动操作',

    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '用户表，权限服务内部维护的用户表，为了和外部服务解耦';