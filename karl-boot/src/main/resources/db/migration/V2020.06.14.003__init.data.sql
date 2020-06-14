delete from T_SYS_ROLE where OBJECT_ID = 'admin';
insert into T_SYS_ROLE (OBJECT_ID, NAME, CODE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY)
values ( 'admin', '管理员', 'admin',current_timestamp, 'system', current_timestamp, 'system');

delete from T_SYS_USER where OBJECT_ID = 'admin';
insert into T_SYS_USER
    (OBJECT_ID, NAME, LOGIN_NAME, PASSWORD, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY)
values ('admin','管理员', 'admin', '$2a$10$XMCDA6VHGAD4pzBocDkAsOY/HxEmQc7ddgDCVEEZ/Pi32.MXr2hf.', current_timestamp, 'system', current_timestamp, 'system');

delete from T_SYS_USER_ROLE_R where OBJECT_ID = 'admin';
insert into T_SYS_USER_ROLE_R (OBJECT_ID, USER_ID, ROLE_ID, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY)
values ('admin', 'admin', 'admin', current_timestamp, 'system', current_timestamp, 'system');
