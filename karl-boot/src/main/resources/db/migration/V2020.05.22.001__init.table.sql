create table T_USERS
(
    OBJECT_ID    varchar(64)  not null,
    CREATE_BY    varchar(256) null,
    CREATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP,
    UPDATE_TIME  DATETIME     not null default CURRENT_TIMESTAMP,
    DATA_VERSION BIGINT       not null default 0,

    LOGIN_NAME   varchar(256) not null,
    NAME         varchar(256) null,
    EMAIL        varchar(256) null,
    PASSWORD     varchar(256) null,
    PHONE_NUMBER varchar(100) null,

    constraint T_USER_pk
        primary key (OBJECT_ID)
);

