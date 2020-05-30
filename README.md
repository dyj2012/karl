# 说明
## Base工程
    统一REST API操作
        GET 查询/导出
        POST 插入/导入
        PUT 更新
        DELTE 删除
        
    统一的增删查改REST API
        GET http://ip:port/v1/demos 查询列表数据
            参数
                query=name:like:2,code:like:2   条件参数
                page=total:true,current:2,size:2 分页参数
                page=all 导出全部
                field=name,email
                orderBy=name,code:asc
        GET http://ip:port/v1/demos/{id} 查询一个数据
        
        POST http://ip:port/v1/demos 插入一个数据
        PUT http://ip:port/v1/demos/{id} 更新一个数据
        DELTE http://ip:port/v1/demos/{id} 删除一个数据
            

    统一的excel导入导出REST API
        GET http://ip:port/v1/demos/exportTemplate 下载导入模板
        GET http://ip:port/v1/demos/export 导出数据,可分页导出
        POST http://ip:port/v1/demos/imports excle导入数据
    
##Core工程
    用户
        增删查改
        导入导出
    机构
        增删查改
        导入导出
    角色
    菜单  
    权限
        登录和注销
        Token验证
     

##module
    路由收集器
    手环
    学生
    教室
            