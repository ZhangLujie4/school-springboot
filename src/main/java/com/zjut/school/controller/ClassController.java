package com.zjut.school.controller;

import com.zjut.school.VO.ParamVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.VO.ScheduleVO;
import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dto.ParamDTO;
import com.zjut.school.service.InstituteMajorService;
import com.zjut.school.service.LessonService;
import com.zjut.school.utils.ResultVOUtil;
import com.zjut.school.utils.ScheduleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 17:32
 */
@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private InstituteMajorService instituteMajorService;

    /**
     * 学生课表
     * @param classId
     * @return
     */
    @GetMapping("/schedule/{classId}")
    public ResultVO<List<ScheduleVO>> lessons(@PathVariable("classId") String classId) {
        List<LessonInfo> lessonInfoList = lessonService.getByClassId(classId);
        List<ScheduleVO> scheduleVOList = ScheduleUtil.getScheduleList(lessonInfoList);

        return ResultVOUtil.success(scheduleVOList);
    }

    /**
     * 学生课程select列表
     * @param classId
     * @return
     */
    @GetMapping("/lessons/{classId}")
    public ResultVO<List<ParamVO>> getLessonList(@PathVariable("classId") String classId) {
        List<ParamVO> paramVOList = lessonService.getByClassId(classId)
                .stream().map(e->{
                    ParamVO paramVO = new ParamVO();
                    paramVO.setLabel(e.getLessonName());
                    paramVO.setValue(e.getLessonNum());
                    return paramVO;
                }).collect(Collectors.toList());
        Set<ParamVO> lessons = new HashSet<>();
        lessons.addAll(paramVOList);
        return ResultVOUtil.success(lessons);
    }

    @GetMapping("/allSelect")
    public ResultVO<List<ParamDTO>> getSelect() {
        return ResultVOUtil.success(instituteMajorService.getSelect());
    }
}
