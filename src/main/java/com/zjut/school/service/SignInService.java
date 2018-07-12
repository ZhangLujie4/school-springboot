package com.zjut.school.service;

import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dataobject.OnlineSign;
import com.zjut.school.dataobject.SignIn;

import java.util.Date;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/27 18:12
 */
public interface SignInService {

    void saveOnlineSign(String lessonId, double teaLon, double teaLat, Integer distance);

    void deleteOnlineSign(String lessonId);

    void create(SignIn signIn);

    void saveCallSign(List<SignIn> signList);

    OnlineSign getOnlineSign(String lessonId);

    List<SignIn> findSigns(String lessonId, Date date, boolean isSign);

    List<SignIn> findByLessonNum(String lessonNum);
}
