# 系统角色初始化
insert into role (par_id, level, name, status, description, create_by, update_by)
values (0, 0, '平台超级管理员', 0, '该角色具有平台所有权限，允许创建下级角色', 0, 0),
       (0, 0, '商家负责人', 0, '入驻到平台的商家的负责人，具有商家端后台管理的所有权限，允许建立下级角色。', 0, 0),
       (0, 0, '消费者', 0, '普通用户，具有正常使用商城前台的所有权限，不具有后台管理的任何权限。', 0, 0);


insert into role (par_id, level, name, status, description, create_by, update_by)
values (2, 1, '市场部主管', 0, '负责管理商品', 0, 0),
       (2, 1, '财务部主管', 0, '，负责管理订单。', 0, 0),
       (2, 1, '仓储物流部主管', 0, '负责管理物流。', 0, 0);