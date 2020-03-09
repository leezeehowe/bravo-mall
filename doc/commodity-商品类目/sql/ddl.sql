/**
  类目表
 */
create table category
(
    id            bigint auto_increment primary key comment '主键，自增',
    parent_id     bigint comment '上级类目的id， 0表示无上级类目即一级类目',
    name          varchar(64) comment '类目名称',
    level         int(1) comment '标识自己所在的层次，即自己属于第几级类目。0->1级类目，1->2级类目',
    product_count int      default 0 comment '该类目下的商品数量，等于该类目下的所有子树的类目节点的该值之和',
    product_unit  varchar(64) comment '该类目的商品单位，如服装类目该值则为 件',
    nav_status    int(1) comment '是否显示在导航栏；0->不显示； 1->显示',
    show_status   int(1) comment '显示状态：0->不显示；1->显示',
    sort          int default 1 comment '排序',
    icon          varchar(255) comment '图片',
    keywords      varchar(255) comment '关键字, 逗号分割',
    description   text comment '描述',
    create_time   datetime default current_timestamp comment '创建时间',
    update_time   datetime default current_timestamp on update current_timestamp comment '最后一次更新时间'
) comment '类目表';

/**
  属性
 */
create table pms_product_attribute
(
    id                   bigint not null auto_increment,
    product_attribute_category_id bigint comment '商品属性分类id',
    name                 varchar(64) comment '名称',
    select_type          int(1) comment '属性选择类型：0->唯一；1->单选；2->多选；对应属性和参数意义不同；',
    input_type           int(1) comment '属性录入方式：0->手工录入；1->从列表中选取',
    input_list           varchar(255) comment '可选值列表，以逗号隔开',
    sort                 int comment '排序字段：最高的可以单独上传图片',
    filter_type          int(1) comment '分类筛选样式：1->普通；1->颜色',
    search_type          int(1) comment '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
    related_status       int(1) comment '相同属性产品是否关联；0->不关联；1->关联',
    hand_add_status      int(1) comment '是否支持手动新增；0->不支持；1->支持',
    type                 int(1) comment '属性的类型；0->规格；1->参数',
    primary key (id)
);

/**
  属性值
 */
create table pms_product_attribute_value
(
    id                   bigint not null auto_increment,
    product_id           bigint comment '商品id',
    product_attribute_id bigint comment '商品属性id',
    value                varchar(64) comment '手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开',
    primary key (id)
);


create table pms_product_category_attribute_relation
(
    id                   bigint not null auto_increment,
    product_category_id  bigint comment '商品分类id',
    product_attribute_id bigint comment '商品属性id',
    primary key (id)
);

/**
  品牌表
 */
create table pms_brand
(
    id                   bigint not null auto_increment,
    name                 varchar(64) comment '名称',
    first_letter         varchar(8) comment '首字母',
    sort                 int comment '排序',
    factory_status       int(1) comment '是否为品牌制造商：0->不是；1->是',
    show_status          int(1) comment '是否显示',
    product_count        int comment '产品数量',
    product_comment_count int comment '产品评论数量',
    logo                 varchar(255) comment '品牌logo',
    big_pic              varchar(255) comment '专区大图',
    brand_story          text comment '品牌故事',
    primary key (id)
);

/**
  描述了一个类目下属性和属性值的数量
 */
create table pms_product_attribute_category
(
    id                   bigint not null auto_increment,
    name                 varchar(64) comment '名称',
    attribute_count      int comment '属性数量',
    param_count          int comment '参数数量',
    primary key (id)
);