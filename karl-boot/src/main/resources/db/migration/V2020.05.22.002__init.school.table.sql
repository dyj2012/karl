-- create table T_MODEL_DEMO
-- (
--     OBJECT_ID    varchar(64)  not null COMMENT '主键',
--     CREATE_BY    varchar(256) null COMMENT '创建者',
--     CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
--     UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
--     DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',
--
--     NAME         varchar(256) null COMMENT '名称',
--     CODE        varchar(256) null COMMENT '编码',
--
--     constraint T_MODEL_DEMO_pk
--         primary key (OBJECT_ID)
-- );

create table T_SC_CLASSROOM
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(500) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',

    constraint T_SC_CLASSROOM_pk
        primary key (OBJECT_ID)
);

create table T_SC_SCHOOL
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(500) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',
    ADDR        varchar(500) null COMMENT '地址',

    constraint T_SC_SCHOOL_pk
        primary key (OBJECT_ID)
);

create table T_SC_PEOPLE
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(64) null COMMENT '名称',
    SEX        varchar(5) null COMMENT '性别',
    ROLE_ID        varchar(64) null COMMENT '角色ID',
    SCHOOL_ID        varchar(64) null COMMENT '学校ID',
    CLASSROOM_ID        varchar(64) null COMMENT '教室ID',


    constraint T_SC_PEOPLE_pk
        primary key (OBJECT_ID)
);

create table T_CO_RECORD
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    WRISTBAND_ID         varchar(64) null COMMENT '手环ID',
    RECORD_TIME        DATETIME null COMMENT '记录时间',
    TEMPERATURE        DECIMAL(18,2) null COMMENT '体温',

    constraint T_CO_RECORD_pk
        primary key (OBJECT_ID)
);

create table T_CO_WRISTBAND
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    STATUS        varchar(50) not null COMMENT '状态' default '正常',
    PEOPLE_ID        varchar(512) null COMMENT '人ID',
    ROUTE_ID        varchar(64) null COMMENT '路由发送器ID',

    constraint T_CO_WRISTBAND_pk
        primary key (OBJECT_ID)
);

create table T_CO_ROUTE
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(256) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',
    STATUS        varchar(50) not null COMMENT '状态' default '正常',

    constraint T_CO_ROUTE_pk
        primary key (OBJECT_ID)
);
