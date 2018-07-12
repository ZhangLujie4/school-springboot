package com.zjut.school.utils;

import com.zjut.school.dataobject.StudentUser;

import java.util.Comparator;

/**
 * Created by 张璐杰
 * 2018/4/18 16:19
 */
public class stuComparator implements Comparator<StudentUser> {

    @Override
    public int compare(StudentUser o1, StudentUser o2) {

        int i = o1.getClassId().compareTo(o2.getClassId());
        if (i == 0) {
            i = o1.getStuName().compareTo(o2.getStuName());
        }
        return i;
    }
}
