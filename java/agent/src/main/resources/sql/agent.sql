#############################################################
#                  MODEL       : agent                      #
#                  AUTHOR      : zhongxiaojian              #
#                  DATE        : 20180409                   #
#############################################################
set names utf8;

###########  创建数据库 ##############
drop database if exists agent;
create database if not exists agent character set utf8;

############### 建表 ################
use agent;

#操作员角色表
create table if not exists `operator_role`
(
  #角色ID
  idx        int auto_increment,
  #角色名
  name       char(32) not null,
  #权限(权限具体与各个管理模块ID对应，拥有该模块ID串则具有该操作权限，ID间用"/"竖杠隔开，*号表示可使用所有权限（超级用户）)
  permission TEXT     not null,
  #创建者
  creater    char(64) not null,
  #主键
  primary key (idx)
) ENGINE = InnoDB default charset = utf8;

insert intO operator_role (name, permission, creater) values ('超级管理员', '*', '');
#操作员表
create table if not exists `operators`
(
  #操作员ID(即用户名)
  id  char(64) not null,
  #角色ID
  role_id int  not null,
  #登陆密码
  password char(16) not null,
  #联系方式
  phone char(16) default '',
  #住址
  addr char(64) default '',
  #登录时间
  login_utc bigint default 0,
  #创建者
  creater char(64) not null,
  #主键
  primary key (id),
  #索引
  key (role_id),
  #外键约束-级联删除
  foreign key (role_id) references operator_role (idx) on delete cascade on update cascade
) ENGINE = InnoDB default charset = utf8;
insert intO operators (id, role_id, password, creater) values ('root', 1, '1', '');

#操作日志表
create table if not exists `operate_log`
(
  #自增索引
  idx bigint unsigned auto_increment,
  #操作员id
  operator_id char(64) not null references operators (id),
  #操作时间
  utc bigint,
  #描述
  description varchar(1024),
  #主键
  primary key (idx)
) ENGINE = InnoDB default charset = utf8;

