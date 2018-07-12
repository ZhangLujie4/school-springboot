package com.zjut.school.VO;

import com.zjut.school.dataobject.LessonInfo;
import lombok.Data;

/**
 * Created by 张璐杰
 * 2018/4/21 23:09
 */

@Data
public class ScheduleVO {

    private Integer id;

    private LessonInfo monday;

    private LessonInfo tuesday;

    private LessonInfo wednesday;

    private LessonInfo thursday;

    private LessonInfo friday;

    public ScheduleVO() {}

    public ScheduleVO(Integer id, LessonInfo monday, LessonInfo tuesday,
                      LessonInfo wednesday, LessonInfo thursday, LessonInfo friday) {
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
}
