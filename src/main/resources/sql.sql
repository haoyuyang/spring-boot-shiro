CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) DEFAULT '' COMMENT '用户名',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别',
  `role_id` int(11) DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT '用户表';

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT '角色表';

CREATE TABLE `t_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `authority_name` varchar(64) DEFAULT '' COMMENT '权限名称',
  `icon` varchar(255) DEFAULT '' COMMENT '图标',
  `uri` varchar(255) DEFAULT '' COMMENT '请求uri',
  `permission` varchar(1000) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT '权限表';

CREATE TABLE `t_role_authority` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `authority_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`role_id`,`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色-权限表';