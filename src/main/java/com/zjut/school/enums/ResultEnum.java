package com.zjut.school.enums;

import lombok.Getter;

/**
 * Created by 张璐杰
 * 2018/4/16 11:15
 */

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    OLD_USERNAME_ERROR(1, "原密码错误"),

    NEWS_DETAIL_ERROR(2, "新闻内容不存在"),

    NEWS_EMPTY(3, "学校新闻为空"),

    PARAM_ERROR(4, "参数错误"),

    USERNAME_ERROR(5, "用户名不存在"),

    PASSWORD_ERROR(6, "密码错误"),

    TEACHER_LESSON_EMPTY(7, "该教师无课"),

    CLASS_LESSON_EMPTY(8, "班级课程为空"),

    STUDENT_EMPTY(9, "该班无学生"),

    UPLOAD_FAIL(10, "文件上传失败"),

    FILE_NOT_EXIST(11, "文件不存在"),

    DOWNLOAD_FAIL(12, "文件下载失败"),

    EXCEL_ERROR(13, "excel解析错误"),

    TEACHER_NUM_ERROR(14, "教师编号错误"),

    STUDENT_NUM_ERROR(15, "学生编号错误"),

    EXCEL_UPLOAD_FAIL(16, "EXCEL上传失败"),

    INSTITUTE_ERROR(17, "学院查询错误"),

    MAJOR_ERROR(18, "专业查询错误"),

    FILE_DELETE_FAIL(19, "文件删除失败"),

    CLASSID_ERROR(20, "找不到学生对应的班级编号"),

    SIGN_OVER_TIME(21, "签到失败，已超时"),

    TIME_FORMAT_ERROR(22, "时间格式化错"),

    ONLINE_SIGN_EMPTY(23, "在线签到课程为空"),

    STUDENT_NOT_FIND(24, "不能找到学生信息"),

    TEACHER_NOT_FIND(25, "不能找到教师信息"),

    STUDENT_ID_ERROR(26, "学号不能重复"),

    TEACHER_ID_ERROR(27, "教师工号不能重复"),

    SIGN_IS_NULL(28, "签到信息为空")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
