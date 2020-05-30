create table T_MODEL_DEMO
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(256) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',

    constraint T_MODEL_DEMO_pk
        primary key (OBJECT_ID)
);

create table T_SYS_USERS
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    LOGIN_NAME   varchar(256) not null COMMENT '登录名',
    NAME         varchar(256) null COMMENT '名称',
    EMAIL        varchar(256) null COMMENT '邮箱',
    PASSWORD     varchar(256) null COMMENT '密码',
    PHONE_NUMBER varchar(100) null COMMENT '电话号码',

    constraint T_USER_pk
        primary key (OBJECT_ID)
);

create table T_SYS_ORG
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(256) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',

    constraint T_SYS_ORG_pk
        primary key (OBJECT_ID)
);

create table T_SYS_ROLE
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(256) null COMMENT '名称',
    CODE        varchar(256) null COMMENT '编码',

    constraint T_SYS_ROLE_pk
        primary key (OBJECT_ID)
);

create table T_SYS_MENU
(
    OBJECT_ID    varchar(64)  not null COMMENT '主键',
    CREATE_BY    varchar(256) null COMMENT '创建者',
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP COMMENT '修改时间',
    DATA_VERSION BIGINT       not null default 0 COMMENT '版本号',

    NAME         varchar(256) null COMMENT '名称',
    URL        varchar(512) null COMMENT '路径',
    POSITION         BIGINT null COMMENT '位置',

    constraint T_SYS_MENU_pk
        primary key (OBJECT_ID)
);

