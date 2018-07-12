package com.zjut.school.VO;

import lombok.Data;

/**
 * Created by 张璐杰
 * 2018/4/28 10:41
 */

@Data
public class LessonVO {

    private String lessonId;

    private String lessonNum;

    private String classId;

    private Integer lessonWeekday;

    private Integer lessonStart;

    private Integer lessonSeveral;

    private String lessonPlace;

    private String teaId;

    private String lessonName;

    private Double teaLon;

    private Double teaLat;

    private Integer distance;
}
