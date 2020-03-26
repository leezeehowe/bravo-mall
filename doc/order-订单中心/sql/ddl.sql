/**
  订单表
 */
create table order
(
    id                   bigint auto_increment primary key comment '订单id',
    order_sn             varchar(64) comment '订单编号',
    create_time          datetime comment '提交时间',
    member_username      varchar(64) comment '用户帐号',
    total_amount         decimal(10,2) comment '订单总金额',
    pay_amount           decimal(10,2) comment '应付金额（实际支付金额）',
    pay_type             int(1) comment '支付方式：0->未支付；1->支付宝；2->微信',
    status               int(1) comment '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    delivery_company     varchar(64) comment '物流公司(配送方式)',
    delivery_sn          varchar(64) comment '物流单号',
    receiver_name        varchar(100) not null comment '收货人姓名',
    receiver_phone       varchar(32) not null comment '收货人电话',
    receiver_post_code   varchar(32) comment '收货人邮编',
    receiver_province    varchar(32) comment '省份/直辖市',
    receiver_city        varchar(32) comment '城市',
    receiver_region      varchar(32) comment '区',
    receiver_detail_address varchar(200) comment '详细地址',
    note                 varchar(500) comment '订单备注',
    confirm_status       int(1) comment '确认收货状态：0->未确认；1->已确认',
    payment_time         datetime comment '支付时间',
    delivery_time        datetime comment '发货时间',
    receive_time         datetime comment '确认收货时间',
    comment_time         datetime comment '评价时间',
    modify_time          datetime comment '修改时间'
);

/**
  订单项表
 */
create table order_item
(
    id                   bigint auto_increment primary key ,
    order_id             bigint comment '订单id',
    order_sn             varchar(64) comment '订单编号',
    product_id           bigint comment '商品id',
    product_quantity     int comment '购买数量',
    product_category_id  bigint comment '商品分类id'
);