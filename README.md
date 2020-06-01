# 说明
## Base工程
- 统一 REST API 操作

   | 操作 | 说明 |
   |  --- |  --- |
   | GET | 查询/导出 |
   | GET  | 查询/导出 |
   | POST  | 插入/导入 |
   | PATCH |  更新 |
   | DELTE |  删除 |

- 统一的增删查改 REST API

    | 操作 | URL | 说明  |
    |  --- | --- | --- |
    | GET | /demos | 查询列表数据 |
    | GET |  /demos/{id} | 查询一个数据|
    | POST |  /demos | 插入一个数据|
    | POST |  /demos/batch | 插入多个数据|
    | PATCH |  /demos/{id} | 更新一个数据|
    | DELTE |  /demos/{id} | 删除一个数据|

- GET操作参数说明

   | 参数 | 示例 | 说明|
   |  --- | --- | --- |
   | query  | query=name:like:2,code:like:2 | 条件参数|
   | page | page=total:true,current:2,size:2 | 分页参数|
   | page | page=all | 查询全部 |
   | field | field=name,email | 查询列|
   | orderBy | orderBy=name,code:asc | 排序|

- 统一的excel导入导出 REST API

  | 操作 | URL | 说明|
  |  --- | --- | --- |
  | GET | /demos/exportTemplate  | 下载excle导入模板 |
  | GET | /demos/export  | excel导出数据,可分页导出 |
  | POST | /demos/imports   | excel导入数据 |
  
- 表基类
  
    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
  | objectId | 主键 | 字符串 | UUID | 不为空 |
  | createTime | 创建时间 | 日期时间 | 当前时间 | 不为空 |
  | createBy | 创建人 | 字符串 | system | 不为空 |
  | updateTime | 更新时间 | 日期时间 | 当前时间 | 不为空 |
  | updateBy | 更新人 | 字符串 | system | 不为空 |
  
---
## Core工程
- 用户(/users)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | loginName | 登录名 | 字符串 | | 唯一,不为空 |
    | password | 密码 | 字符串 |
    | email | 邮箱 | 字符串 |
    | phoneNumber | 电话 | 字符串 |
    
- 机构(/orgs)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | code | 编码 | 字符串 |

- 角色(/roles)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | code | 编码 | 字符串 |

- 菜单 (/menus) 

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | url | 路径 | 字符串 |
    | position | 位置 | 数值 | 

- 权限(/auth)

  | 动作 | 操作 | URL     | 返回|
  | ---- | ---- | ---- | ---- |
  | 登录 | POST| /login | String |

  Token验证说明
  
  | Header Key | value   |
  | ----  | ---- |
  | LoginToken  | String |

---
## Module工程
- 路由收集器(/routes)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | code | 编码 | 字符串 |
    | status | 状态 | 字符串 | 
    
- 手环(/wristbands)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | peopleId | 人员ID | 字符串 |
    | routeId | 路由ID | 字符串 |
    | status | 状态 | 字符串 | 
    
- 人员(/people)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | sex | 性别 | 字符串 |
    | age | 年龄 | 数值 |
    | roleId | 角色ID | 字符串 |
    | schoolId | 学校ID | 字符串 |
    | classroomId | 教室ID | 字符串 |
    | status | 状态 | 字符串 | 
    
- 教室(/classrooms)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | code | 编码 | 字符串 |
    
- 学校(/schools)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | name | 名字 | 字符串 | |不为空 |
    | code | 编码 | 字符串 |
    | address | 地址 | 字符串 |
    
- 记录(/records)

    | 属性 | 名称 | 类型 | 默认值 | 约束 |
    | --- | --- | --- | --- | --- |
    | wristbandId | 手环ID | 字符串 |
    | recordTime | 记录时间 | 日期时间 |
    | temperature | 体温 | 浮点 |
    