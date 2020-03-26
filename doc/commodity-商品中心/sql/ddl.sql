/**
  类目表
 */
create table if not exists category
(
    id bigint auto_increment comment '主键，自增'
        primary key,
    parent_id bigint null comment '上级类目的id， 0表示无上级类目即一级类目',
    name varchar(64) null comment '类目名称',
    level int(1) null comment '标识自己所在的层次，即自己属于第几级类目。0->1级类目，1->2级类目',
    product_count int default 0 null comment '该类目下的商品数量，等于该类目下的所有子树的类目节点的该值之和',
    product_unit varchar(64) null comment '该类目的商品单位，如服装类目该值则为 件',
    nav_status int(1) null comment '是否显示在导航栏；0->不显示； 1->显示',
    show_status int(1) null comment '显示状态：0->不显示；1->显示',
    sort int default 1 null comment '排序',
    icon varchar(255) null comment '图片',
    keywords varchar(255) null comment '关键字, 逗号分割',
    description text null comment '描述',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '最后一次更新时间'
)
    comment '类目表';

/**
  商品表
 */
create table product
(
    id                   bigint auto_increment primary key ,
    category_id          bigint not null comment '所属分类id',
    name                 varchar(64) not null comment '商品名称',
    pic                  varchar(255) comment '图片',
    product_sn           varchar(64) not null comment '货号',
    publish_status       int(1) comment '上架状态：0->下架；1->上架',
    sale                 int comment '销量',
    price                decimal(10,2) comment '价格',
    promotion_price      decimal(10,2) comment '促销价格',
    sub_title            varchar(255) comment '副标题',
    description          text comment '商品描述',
    original_price       decimal(10,2) comment '市场价',
    unit                 varchar(16) comment '单位',
    keywords             varchar(255) comment '关键字',
    note                 varchar(255) comment '备注',
    album_pics           varchar(255) comment '画册图片，连产品图片限制为5张，以逗号分割',
    detail_title         varchar(255) comment '详情标题',
    detail_desc          text comment '详情描述',
    product_category_name varchar(255) comment '产品分类名称'
);