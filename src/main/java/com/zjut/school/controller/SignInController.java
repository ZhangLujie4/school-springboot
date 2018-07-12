package com.zjut.school.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjut.school.VO.LessonVO;
import com.zjut.school.VO.ParamVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.constant.DataLocalConstant;
import com.zjut.school.constant.StudentConstant;
import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dataobject.OnlineSign;
import com.zjut.school.dataobject.SignIn;
import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.service.LessonService;
import com.zjut.school.service.SignInService;
import com.zjut.school.service.StudentService;
import com.zjut.school.service.WebSocket;
import com.zjut.school.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/25 8:59
 */

@RestController
@RequestMapping("/signIn")
@Slf4j
public class SignInController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StudentService studentService;

    /**
     * 在线签到开启
     * @param lessonId
     * @param teaLon
     * @param teaLat
     * @param distance
     * @return
     */
    @PostMapping("/online")
    public ResultVO saveOnlineSign(@RequestParam("lessonId") String lessonId,
                                   @RequestParam("teaLon") double teaLon,
                                   @RequestParam("teaLat") double teaLat,
                                   @RequestParam("distance") Integer distance) {
        log.info("{},{},{}",lessonId, teaLon, teaLat, distance);
        signInService.saveOnlineSign(lessonId, teaLon, teaLat, distance);
        return ResultVOUtil.success();
    }

    /**
     * 删除onlineSign
     * @param lessonId
     * @return
     */
    @DeleteMapping("/delete/{lessonId}")
    public ResultVO deleteOnlineSign(@PathVariable("lessonId") String lessonId) {
        signInService.deleteOnlineSign(lessonId);
        return ResultVOUtil.success();
    }

    /**
     * 学生在线签到
     * @param lessonId
     * @param lessonNum
     * @param stuName
     * @param stuId
     * @return
     */
    @PostMapping("/stuCreate")
    public ResultVO createSignIn(@RequestParam("lessonId") String lessonId,
                                 @RequestParam("lessonNum") String lessonNum,
                                 @RequestParam("stuName") String stuName,
                                 @RequestParam("stuId") String stuId) {
        OnlineSign onlineSign = signInService.getOnlineSign(lessonId);
        if (onlineSign == null) {
            throw new SchoolException(ResultEnum.SIGN_OVER_TIME);
        }
        LessonInfo lesson = lessonService.getLessonInfo(lessonId);
        SignIn signIn = new SignIn();
        Date time = new Date();
        String timeFormat = new SimpleDateFormat("yyyyMMdd").format(time);
        signIn.setSignId(timeFormat+lessonId+stuId);
        signIn.setSignDate(time);
        signIn.setStuName(stuName);
        signIn.setLessonNum(lessonNum);
        signIn.setIsSign(StudentConstant.IS_SIGN);
        signIn.setStuId(stuId);
        signIn.setLessonId(lessonId);
        signInService.create(signIn);
        webSocket.sendToUser(lesson.getTeaId(), "signSuccess");

        return ResultVOUtil.success();
    }

    /**
     * 关闭在线签到
     * @param lessonId
     * @param signDate
     * @return
     */
    @PostMapping("/outline")
    public ResultVO saveOutlineSign(@RequestParam("lessonId") String lessonId,
                                    @RequestParam("signDate") String signDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(signDate);
            String time = new SimpleDateFormat("yyyyMMdd").format(date);
            List<String> signStudent = signInService.findSigns(lessonId, date, StudentConstant.IS_SIGN)
                    .stream().map(e->e.getStuId()).collect(Collectors.toList());
            List<StudentUser> alreadyStudent = studentService.findByStuIdIn(signStudent);
            LessonInfo lessonInfo = lessonService.getLessonInfo(lessonId);
            List<StudentUser> allStudent = studentService.findByClassId(lessonInfo.getClassId());
            allStudent.removeAll(alreadyStudent);
            for (StudentUser student : allStudent) {
                SignIn signIn = new SignIn();
                signIn.setSignId(time+lessonId+student.getStuId());
                signIn.setSignDate(date);
                signIn.setLessonId(lessonId);
                signIn.setLessonNum(lessonId.substring(0, DataLocalConstant.LESSON_NUM_LENGTH));
                signIn.setIsSign(StudentConstant.NOT_SIGN);
                signIn.setStuId(student.getStuId());
                signIn.setStuName(student.getStuName());
                signInService.create(signIn);
            }
            return ResultVOUtil.success();
        } catch (ParseException e) {
            throw new SchoolException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }

    /**
     * 当前已签到人数
     * @param lessonId
     * @param signDate
     * @return
     */
    @PostMapping("/nowAmount")
    public ResultVO<Map<String, Integer>> getNowAmount(@RequestParam("lessonId") String lessonId,
                                 @RequestParam("signDate") String signDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(signDate);
            List<String> signStudent = signInService.findSigns(lessonId, date, StudentConstant.IS_SIGN)
                    .stream().map(e->e.getStuId()).collect(Collectors.toList());

            Set<String> set = new HashSet<>();
            set.addAll(signStudent);
            //TODO 这里好像可以去掉
            log.info("{}", set);
            Map<String, Integer> map = new HashMap<>();
            map.put("nowAmount", set.size());
            return ResultVOUtil.success(map);
        } catch (ParseException e) {
            throw new SchoolException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }

    /**
     * 获取课程签到点位置
     * @param lessonId
     * @return
     */
    @GetMapping("/point/{lessonId}")
    public ResultVO<Map<String, Double>> getPoint(@PathVariable("lessonId") String lessonId) {
        Map<String, Double> map = new HashMap<>();
        OnlineSign onlineSign = signInService.getOnlineSign(lessonId);
        if (onlineSign == null) {
            throw new SchoolException(ResultEnum.SIGN_OVER_TIME);
        }

        map.put("lon", onlineSign.getTeaLon());
        map.put("lat", onlineSign.getTeaLat());

        return ResultVOUtil.success(map);
    }

    /**
     * 获取可签到课程
     * @param classId
     * @return
     */
    @GetMapping("/available/{classId}")
    public ResultVO<LessonVO> availableOnlineSign(@PathVariable("classId") String classId) {
        List<String> lessons  = lessonService.getByClassId(classId)
                .stream().map(e->e.getLessonId()).collect(Collectors.toList());
        log.info("{}",lessons);
        LessonInfo lessonInfo = new LessonInfo();
        OnlineSign onlineSign = new OnlineSign();
        LessonVO lessonVO = new LessonVO();
        for (String lessonId : lessons) {
            if (signInService.getOnlineSign(lessonId) != null) {
                lessonInfo = lessonService.getLessonInfo(lessonId);
                onlineSign = signInService.getOnlineSign(lessonId);
                break;
            }
        }
        BeanUtils.copyProperties(onlineSign, lessonVO);
        BeanUtils.copyProperties(lessonInfo, lessonVO);

        if (StringUtils.isEmpty(lessonVO.getLessonId())) {
            throw new SchoolException(ResultEnum.ONLINE_SIGN_EMPTY);
        }

        return ResultVOUtil.success(lessonVO);
    }

    /**
     * 获取学生的签到情况
     * @param lessonNum
     * @param stuId
     * @return
     */
    @PostMapping("/list")
    public ResultVO<List<SignIn>> getStuLessonSign(@RequestParam("lessonNum") String lessonNum,
                                                   @RequestParam("stuId") String stuId) {
        List<SignIn> signInList = signInService.findByLessonNum(lessonNum);
        if (CollectionUtils.isEmpty(signInList)) {
            throw new SchoolException(ResultEnum.SIGN_IS_NULL);
        }
        List<SignIn> result = new ArrayList<>();
        for (SignIn signIn : signInList) {
            if (signIn.getStuId().equals(stuId)) {
                result.add(signIn);
            }
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 获得某个课程的签到情况
     * @param lessonNum
     * @return
     */
    @GetMapping("/list/{lessonNum}")
    public ResultVO<List<SignIn>> getLessonSign(@PathVariable("lessonNum") String lessonNum) {
        List<SignIn> signInList = signInService.findByLessonNum(lessonNum);
        if (CollectionUtils.isEmpty(signInList)) {
            throw new SchoolException(ResultEnum.SIGN_IS_NULL);
        }
        return ResultVOUtil.success(signInList);
    }

    /**
     * 签到-可选择的时间
     * @param lessonNum
     * @return
     */
    @GetMapping("/time/{lessonNum}")
    public ResultVO<List<ParamVO>> getTimeList(@PathVariable("lessonNum") String lessonNum) {
        List<ParamVO> paramVOList = signInService.findByLessonNum(lessonNum)
                .stream().map(e->{
                    ParamVO paramVO = new ParamVO();
                    String time = new SimpleDateFormat("yyyy-MM-dd").format(e.getSignDate());
                    paramVO.setValue(time);
                    paramVO.setLabel(time);
                    return paramVO;
                }).collect(Collectors.toList());
        Set<ParamVO> result = new HashSet<>();
        result.addAll(paramVOList);
        return ResultVOUtil.success(result);
    }

    /**
     * 签到-可选择学生
     * @param lessonNum
     * @return
     */
    @GetMapping("/stuId/{lessonNum}")
    public ResultVO<List<ParamVO>> getStuIdList(@PathVariable("lessonNum") String lessonNum) {
        LessonInfo lessonInfo = lessonService.getByLessonNum(lessonNum);
        if (lessonInfo == null) {
            return ResultVOUtil.success();
        }
        List<ParamVO> paramVOList = studentService.findByClassId(lessonInfo.getClassId())
                .stream().map(e -> {
                    ParamVO paramVO = new ParamVO();
                    paramVO.setLabel(e.getStuName());
                    paramVO.setValue(e.getStuId());
                    return paramVO;
                }).collect(Collectors.toList());
        return ResultVOUtil.success(paramVOList);
    }

    @PostMapping("/callSign")
    public ResultVO submitCallSign(@RequestParam("signList") String signList) {
        List<SignIn> signInList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            signInList = gson.fromJson(signList, new TypeToken<List<SignIn>>(){}.getType());
        } catch (Exception e) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(), "参数转换错误");
        }
        signInService.saveCallSign(signInList);
        return ResultVOUtil.success();
    }
}
