package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 3:08
 */

@Entity
@Data
public class LessonInfo {

    @Id
    private String lessonId;

    private String lessonNum;

    private String classId;

    private Integer lessonWeekday;

    private Integer lessonStart;

    private Integer lessonSeveral;

    private String lessonPlace;

    private String teaId;

    private String lessonName;

    public LessonInfo(){}

    public LessonInfo(String lessonId, String lessonNum, String classId, Integer lessonWeekday,
                      Integer lessonStart, Integer lessonSeveral, String lessonPlace,
                      String teaId, String lessonName) {
        this.lessonId = lessonId;
        this.lessonNum = lessonNum;
        this.classId = classId;
        this.lessonWeekday = lessonWeekday;
        this.lessonStart = lessonStart;
        this.lessonSeveral = lessonSeveral;
        this.lessonPlace = lessonPlace;
        this.teaId = teaId;
        this.lessonName = lessonName;
    }
}
