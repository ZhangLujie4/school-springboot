package com.zjut.school.utils;

import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import org.springframework.scheduling.SchedulingException;

/**
 * Created by 张璐杰
 * 2018/4/17 21:14
 */
public class FormatUtil {

    public static String FormatFour(Integer teaNum) {
        if (teaNum <= 0 || teaNum > 10000) {
            throw new SchoolException(ResultEnum.TEACHER_NUM_ERROR);
        } else {
            return String.format("%04d",teaNum);
        }
    }

    public static String FormatTwo(Integer stuNum) {
        if (stuNum <= 0 || stuNum >= 100) {
            throw new SchoolException(ResultEnum.STUDENT_NUM_ERROR);
        } else {
            return String.format("%02d",stuNum);
        }
    }

    public static String subStringFour(String teaId) {
        return teaId.substring(8);
    }
}
