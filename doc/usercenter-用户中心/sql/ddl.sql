create schema if not exists bravo_usercenter collate utf8_general_ci;

/**
  账号表：基础账号
 */
create table if not exists user_fundamental
(
    id int(11) unsigned auto_increment comment '主键，自增'
        primary key,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最后一次更新时间',
    uuid char(36) null comment '用户唯一标识',
    username varchar(20) null comment '用户名，可用于登录',
    password varchar(32) null comment '密码，md5加密',
    reserve text null comment '预留字段，或用来存储第三方系统如github的用户信息',
    email_address varchar(50) null comment '邮箱地址',
    phone_number varchar(25) null comment '手机号码',
    nickname varchar(10) null comment '昵称，仅支持中文、数字、大小写英文字母、下划线',
    avatar varchar(200) null comment '头像地址',
    gender tinyint unsigned null comment '性别，0-未知，1-男性，2-女性',
    country varchar(50) null comment '所在国家',
    province varchar(50) null comment '所在省份',
    city varchar(50) null comment '所在城市',
    is_info_completed tinyint unsigned default 1 null comment '是否完善了信息, 0-是，1-否',
    constraint user_fundamental_email_address_uindex
        unique (email_address),
    constraint user_fundamental_phone_number_uindex
        unique (phone_number),
    constraint user_fundamental_username_uindex
        unique (username)
)
    comment '平台用户信息';

/**
  账号表：微信账号信息表
 */
create table if not exists user_wechat
(
    id int(11) unsigned auto_increment comment '主键，自增'
        primary key,
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最后一次更新时间',
    user_uuid char(36) null comment '平台用户的UUID',
    unionId varchar(50) null comment '微信用户unionId',
    openId varchar(50) null comment '微信小程序用户openId',
    session_key varchar(50) null comment '微信小程序用户secret'
)
    comment '平台用户与微信方用户信息关联';

