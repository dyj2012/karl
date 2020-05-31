# 说明
## Base工程
- 统一REST API操作
 操作 | 说明
 - | -
 GET | 查询/导出
 GET  | 查询/导出
 POST  | 插入/导入
 PATCH |  更新
 DELTE |  删除

- 统一的增删查改REST API
 操作 | URL | 说明
 - | -  | -  
GET | /demos | 查询列表数据  
GET |  /demos/{id} | 查询一个数据
POST |  /demos | 插入一个数据
POST |  /demos/batch | 插入多个数据
PATCH |  /demos/{id} | 更新一个数据
DELTE |  /demos/{id} | 删除一个数据

- GET操作参数说明
参数 | 示例 | 说明
- | - | -
query  | name:like:2,code:like:2 | 条件参数
page | total:true,current:2,size:2 | 分页参数
page | all | 导出全部
field | name,email | 查询列
orderBy | name,code:asc | 排序

- 统一的excel导入导出REST API
 操作 | 说明
  - | - 
 GET | /demos/exportTemplate 下载导入模板
 GET | /demos/export 导出数据,可分页导出
 POST | /demos/imports excle导入数据
---
##Core工程
- 用户(/users)
- 机构(/orgs)
- 角色(/roles)
- 菜单 (/menus) 
- 权限(/auth)
	登录(/login POST)
	Token验证

---
##module工程
- 路由收集器(/routes)
- 手环(/wristbands)
- 人员(/people)
- 教室(/classrooms)
- 学校(/schools)
- 记录(/records)