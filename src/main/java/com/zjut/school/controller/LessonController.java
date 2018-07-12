package com.zjut.school.controller;

import com.zjut.school.VO.ParamVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.VO.ScheduleVO;
import com.zjut.school.VO.StudentVO;
import com.zjut.school.constant.DataLocalConstant;
import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dataobject.SignIn;
import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.service.LessonService;
import com.zjut.school.service.StudentService;
import com.zjut.school.utils.ResultVOUtil;
import com.zjut.school.utils.ScheduleUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/19 1:18
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StudentService studentService;

    /**
     * 返回老师的课程清单用于select(课程结合)
     * @param teaId
     * @return
     */
    @GetMapping("/list/{teaId}")
    public ResultVO<List<ParamVO>> selectByTeaId(@PathVariable("teaId") String teaId) {
        List<ParamVO> paramVOList = lessonService.getByTeaId(teaId)
                .stream().map(e -> {
                    ParamVO paramVO = new ParamVO();
                    paramVO.setLabel(e.getLessonName());
                    paramVO.setValue(e.getLessonNum());
                    return paramVO;
                }).collect(Collectors.toList());
        Set<ParamVO> result = new HashSet<>();
        result.addAll(paramVOList);
        return ResultVOUtil.success(result);
    }

    /**
     * 返回老师的课程清单用于select(课程分开)
     * @param teaId
     * @return
     */
    @GetMapping("/teaList/{teaId}")
    public ResultVO<List<ParamVO>> findByTeaId(@PathVariable("teaId") String teaId) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "星期一");
        map.put(2, "星期二");
        map.put(3, "星期三");
        map.put(4, "星期四");
        map.put(5, "星期五");

        List<LessonInfo> lessonInfoList = lessonService.getByTeaId(teaId);
        if (CollectionUtils.isEmpty(lessonInfoList)) {
            return ResultVOUtil.success(lessonInfoList);
        }
        List<ParamVO> paramVOList = new ArrayList<>();
        for (LessonInfo lessonInfo : lessonInfoList) {
            ParamVO paramVO = new ParamVO();
            paramVO.setValue(lessonInfo.getLessonId());
            Integer start = lessonInfo.getLessonStart();
            Integer end = start + lessonInfo.getLessonSeveral() - 1;
            String label = map.get(lessonInfo.getLessonWeekday())+start+"-"+end+"节"+lessonInfo.getLessonName();
            paramVO.setLabel(label);
            paramVOList.add(paramVO);
        }

        return ResultVOUtil.success(paramVOList);
    }

    /**
     * 课程学生人数
     * @param lessonId
     * @return
     */
    @GetMapping("/amount/{lessonId}")
    public ResultVO<Map<String, Integer>> lessonAmount(@PathVariable("lessonId") String lessonId) {
        LessonInfo lessonInfo = lessonService.getLessonInfo(lessonId);
        Integer amount = studentService.classStudentAmount(lessonInfo.getClassId());
        Map<String, Integer> map = new HashMap<>();
        map.put("amount", amount);
        return ResultVOUtil.success(map);
    }

    /**
     * 返回随机点到名单
     * @param lessonId
     * @param count
     * @return
     */
    @PostMapping("/random")
    public ResultVO<List<SignIn>> getRandomStudent(@RequestParam("lessonId") String lessonId,
                                                  @RequestParam("count") Integer count) {
        List<SignIn> signInList = studentService.getRandomList(lessonId, count)
                .stream().map(e -> {
                    SignIn signIn = new SignIn();
                    signIn.setLessonId(lessonId);
                    signIn.setLessonNum(lessonId.substring(0, DataLocalConstant.LESSON_NUM_LENGTH));
                    signIn.setStuId(e.getStuId());
                    signIn.setStuName(e.getStuName());
                    signIn.setIsSign(false);
                    return signIn;
                }).collect(Collectors.toList());
        return ResultVOUtil.success(signInList);
    }

    /**
     * 教师课表
     * @param teaId
     * @return
     */
    @GetMapping("/schedule/{teaId}")
    public ResultVO<List<ScheduleVO>> findLessonsByTeaIa(@PathVariable("teaId") String teaId) {
        List<LessonInfo> lessonInfoList = lessonService.getByTeaId(teaId);
        List<ScheduleVO> scheduleVOList = ScheduleUtil.getScheduleList(lessonInfoList);

        return ResultVOUtil.success(scheduleVOList);
    }
}
