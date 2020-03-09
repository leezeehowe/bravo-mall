drop table if exists user;
create table user
(
    id bigint unsigned auto_increment primary key comment '主键，自增',
    create_time datetime default current_timestamp comment  '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间',

    uuid char(32) comment '用户唯一标识',
    username varchar(20) comment '用户名，可用于登录',
    password varchar(32) comment '密码，md5加密',
    reserve text comment '预留字段，或用来存储第三方系统如github的用户信息',
    email_address varchar(50) comment '邮箱地址',
    phone_number varchar(25) comment '手机号码',
    nickname varchar(10) comment '昵称，仅支持中文、数字、大小写英文字母、下划线',
    avatar varchar(200) comment '头像地址',
    gender tinyint unsigned comment '性别，0-未知，1-男性，2-女性',
    country varchar(50) comment '所在国家',
    province varchar(50) comment '所在省份',
    city varchar(50) comment '所在城市',

    is_infoCompleted tinyint unsigned comment '是否完善了信息, 0-是，1-否'
) comment '平台用户信息';

drop table if exists user_wechat;
create table user_wechat
(
    id bigint unsigned auto_increment primary key comment '主键，自增',
    create_time datetime default current_timestamp comment  '创建时间',
    update_time datetime default current_timestamp on update current_timestamp comment '最后一次更新时间',

    user_uuid char(32) comment '平台用户的UUID',
    unionId varchar(50) comment '微信用户unionId',
    openId varchar(50) comment '微信小程序用户openId',
    session_key varchar(50) comment '微信小程序用户secret'
) comment '平台用户与微信方用户信息关联';

use bravo_usercenter;
alter table user_fundamental auto_increment = 1;
alter table user_wechat auto_increment = 1;