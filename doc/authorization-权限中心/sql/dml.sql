# 系统角色初始化
insert into role (par_id, level, name, status, description, create_by, update_by)
values (0, 0, '平台超级管理员', 0, '该角色具有平台所有权限，允许创建下级角色', 0, 0),
       (0, 0, '商家负责人', 0, '入驻到平台的商家的负责人，具有商家端后台管理的所有权限，允许建立下级角色。', 0, 0),
       (0, 0, '消费者', 0, '普通用户，具有正常使用商城前台的所有权限，不具有后台管理的任何权限。', 0, 0);

# 初始化商家负责人
insert into role (par_id, level, name, status, description, create_by, update_by)
values (2, 1, '市场部主管', 0, '负责管理商品', 0, 0),
       (2, 1, '财务部主管', 0, '，负责管理订单。', 0, 0),
       (2, 1, '仓储物流部主管', 0, '负责管理物流。', 0, 0);
update role as r set r.sub_count = 3 where r.id = 2;

# 初始化页面资源
insert into webpage_resource(par_id, name, text, path, type, description, level, create_by, update_by)
VALUES (0, 'frontend-resource', '前台资源', '', 0, '前台微信小程序的顶级资源', 0, 0 ,0),
       (0, 'admin-resource', '后台管理系统资源', '', 0, '后台管理系统的顶级资源', 0, 0 ,0),
       (2, 'ams', '权限管理系统', '', 0, '权限管理系统二级资源，其下有角色管理与资源管理', 1, 0, 0),
       (3, 'role', '角色管理', '', 0, '角色管理，角色列表和添加角色等', 2, 0, 0),
       (4, 'role-list', '角色列表', '', 0, '角色列表页', 3, 0, 0),
       (5, 'role-add', '添加角色', '', 0, '添加角色', 4, 0, 0);

