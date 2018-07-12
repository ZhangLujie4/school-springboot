package com.zjut.school.utils;

import com.zjut.school.VO.ScheduleVO;
import com.zjut.school.dataobject.LessonInfo;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/5/29 23:47
 */
public class ScheduleUtil {

    public static List<ScheduleVO> getScheduleList(List<LessonInfo> lessonInfoList) {
        List<ScheduleVO> scheduleVOList = new ArrayList<>();
        LessonInfo lesson = new LessonInfo();
        for (int i = 0; i < 12; i++) {
            scheduleVOList.add(new ScheduleVO((i+1),lesson,lesson,lesson,lesson,lesson));
        }

        if (CollectionUtils.isEmpty(lessonInfoList)) {
            return scheduleVOList;
        }

        for (LessonInfo lessonInfo : lessonInfoList) {
            if (lessonInfo.getLessonWeekday() == 1) {
                scheduleVOList.get(lessonInfo.getLessonStart()-1).setMonday(lessonInfo);
            }
            if (lessonInfo.getLessonWeekday() == 2) {
                scheduleVOList.get(lessonInfo.getLessonStart()-1).setTuesday(lessonInfo);
            }
            if (lessonInfo.getLessonWeekday() == 3) {
                scheduleVOList.get(lessonInfo.getLessonStart()-1).setWednesday(lessonInfo);
            }
            if (lessonInfo.getLessonWeekday() == 4) {
                scheduleVOList.get(lessonInfo.getLessonStart()-1).setThursday(lessonInfo);
            }
            if (lessonInfo.getLessonWeekday() == 5) {
                scheduleVOList.get(lessonInfo.getLessonStart()-1).setFriday(lessonInfo);
            }
        }

        return scheduleVOList;
    }
}
