--学生
create table `student_user` (
	`stu_id` varchar(32) not null comment '学生学号',
	`stu_password` varchar(32) not null comment '学生密码',
	`stu_name` varchar(16) not null comment '学生名字',
	`stu_sex` varchar(8) comment '学生性别',
	`stu_birth` varchar(16) comment '出生年月',	
	`stu_year` int not null comment '入学年份',
	`stu_num` varchar(8) not null comment '班内序号',
	`class_id` varchar(32) not null comment '班级编号',
	`class_num` varchar(8) not null comment '班级',
	`institute_id` varchar(8) not null comment '学院编号',
	`major_id` varchar(8) not null comment '专业编号',
	primary key (`stu_id`)
);

--教师
create table `teacher_user` (
	`tea_id` varchar(32) not null comment '教职工号',
	`tea_password` varchar(32) not null comment '教师密码',
	`tea_name` varchar(16) not null comment '教师姓名',
	`tea_sex` varchar(8) comment '教师性别',
	`tea_birth` varchar(16) comment '出生年月',
	`class_id` varchar(32) comment '班级编号',
	`class_num` varchar(8) comment '班级',
	`major_id` varchar(8) comment '专业编号',
	`tea_num` varchar(8) not null comment '教师院内编号',
	`tea_year` int comment '入职年份',
	`institute_id` varchar(8) not null comment '学院编号',
	`tea_phone` varchar(16) comment '手机号',
	`tea_email` varchar(32) comment '邮箱',
	primary key(`tea_id`)
	unique key (`class_id`)
);

--管理员
create table `admin_user` (
	`username` varchar(32) not null comment '管理员用户名',
	`password` varchar(32) not null comment '管理员密码',
	primary key(`username`)
);

--专业
create table `major_info` (
	`major_id` varchar(8) not null comment '专业编号',
	`institute_id` varchar(16) not null comment '学院id',
	`major_name` varchar(16) not null comment '学院名称',
	primary key (`major_id`)
);

--学院
create table `institute_info` (
	`institute_id` varchar(8) not null comment '学院编号',
	`institute_name` varchar(16) not null comment '学院名称',
	`description` varchar(64) comment '学院资料',
	primary key (`institute_id`),
  unique key (`institute_name`)
);

--学校新闻
create table `news_info` (
	`news_id` int not null auto_increment comment '新闻编号',
	`news_title` varchar(32) not null comment '新闻题目',
	`news_content` varchar(2000) comment '新闻内容',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	primary key (`news_id`)	
);

--班级课表
create table `class_info`(
	`class_id` varchar(32) not null comment '班级编号自增',
	`class_num` varchar(8) not null comment '班级编号',
	`major_id` varchar(8) not null comment '专业编号',
	`institute_id` varchar(8) not null comment '学院编号',
	primary key (`class_id`)
);

--课程
create table `lesson_info` (
	`lesson_id` varchar(32) not null comment '课程编号',
	`lesson_num` varchar(32) not null comment '课程序号',
	`class_id` varchar(32) not null comment '班级编号',
	`lesson_weekday` int not null comment '上课星期',
	`lesson_start` int not null comment '第几节课开始',
	`lesson_several` int not null comment '几节课',
	`lesson_place` varchar(32) not null comment '上课地点',
	`lesson_name` varchar(32) not null comment '课程名称',
	`tea_id` varchar(32) not null comment '教师编号',
	primary key (`lesson_id`)
);



--签到表
create table `sign_in` (
	`sign_id` varchar(128) not null comment '签到编号',
	`sign_date` date not null comment '创建时间',
	`lesson_id` varchar(32) not null comment '课程编号',
	`lesson_num` varchar(32) not null comment '课程序号',
	`stu_name` varchar(16) not null comment '学生名字',
	`stu_id` varchar(32) not null comment '学生id',
	`is_sign` boolean not null comment '是否签到',
	primary key (`sign_id`)
);

--在线签到临时表
create table `online_sign` (
  `lesson_id` varchar(32) not null comment '课程编号',
  `tea_lon` double not null comment '经度',
  `tea_lat` double not null comment '纬度',
  `distance` int not null comment '距离',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  primary key (`lesson_id`)
)

--消息互发
create table `message_info` (
	`message_id` int not null auto_increment,
	`sender` varchar(32) not null comment '发送方',
	`receiver` varchar(32) not null comment '接收方',
	`class_id` varchar(32) comment '班级编号',
	`message` varchar(300) comment '信息',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	primary key (`message_id`)
);

--资料库
create table `data_info` (
	`data_id` int not null auto_increment,
	`data_name` varchar(128) not null comment '文件标题',
	`data_path` varchar(128) not null comment '文件路径',
	`class_id` varchar(32) not null comment '班级编号',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	primary key (`data_id`)
);