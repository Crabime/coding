# 创建数据库
CREATE DATABASE yuanqin;

# 用户表
create table user
(
  id int auto_increment primary key,
  username varchar(50) not null,
  enabled tinyint null,
  constraint user unique (username)
);

# 角色表
create table role
(
  id int auto_increment primary key,
  name varchar(50) not null,
  description varchar(50) null
);

# 资源文件表
create table resc
(
  id int auto_increment primary key,
  res_string varchar(50) not null,
  res_type varchar(10) null,
  description varchar(50) null
);

# 用户角色关联表
create table user_role
(
  user_id int not null,
  role_id int not null,
  primary key (user_id, role_id),
  constraint fk_user_role_user
  foreign key (user_id) references user (id) on delete cascade,
  constraint fk_user_role_role foreign key (role_id) references role (id)
);

# 角色资源关联表
create table role_resc
(
  role_id int not null,
  resc_id int not null,
  primary key (role_id, resc_id),
  constraint fk_role_resc_role foreign key (role_id) references role (id),
  constraint fk_role_resc_resc foreign key (resc_id) references resc (id)
);

# 插入几个用户
INSERT INTO user (id, username, password, status) VALUES (NULL , 'crabime', '123456', 1);
INSERT INTO user (id, username, password, status) VALUES (NULL , 'bill', '123456', 1);

# 插入用户角色关联表
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);

# 插入几个角色
INSERT INTO role (role_name, description) VALUES ('ROLE_ADMIN', 'administrator role');
INSERT INTO role (role_name, description) VALUES ('ROLE_USER', 'normal user');
INSERT INTO role (role_name, description) VALUES ('ROLE_ANONYMOUS', 'anonymous user');

# 插入受保护的资源
INSERT INTO resc (id, resc_name, res_type, description) VALUES (NULL , '/login/**', 'URL', 'anonymous url');
INSERT INTO resc (id, resc_name, res_type, description) VALUES (NULL , '/common/**', 'URL', 'normal url');
INSERT INTO resc (id, resc_name, res_type, description) VALUES (NULL , '/admin/**', 'URL', 'admin url');

# 插入受保护资源与权限之间关联表
INSERT INTO role_resc (role_id, resc_id) VALUES (1, 3);
INSERT INTO role_resc (role_id, resc_id) VALUES (2, 2);
INSERT INTO role_resc (role_id, resc_id) VALUES (3, 1);
