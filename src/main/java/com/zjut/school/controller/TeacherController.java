package com.zjut.school.controller;

import com.zjut.school.VO.ParamVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.VO.TeacherVO;
import com.zjut.school.constant.TeacherConstant;
import com.zjut.school.dataobject.*;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.form.StudentForm;
import com.zjut.school.form.TeacherForm;
import com.zjut.school.service.InstituteMajorService;
import com.zjut.school.service.LessonService;
import com.zjut.school.service.TeacherService;
import com.zjut.school.utils.FormatUtil;
import com.zjut.school.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 10:54
 */

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private InstituteMajorService instituteMajorService;

    @Autowired
    private LessonService lessonService;


    /**
     * 教师登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResultVO<Map<String, String>> login(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {

        TeacherUser teacherUser = teacherService.get(username);

        //用户名不存在
        if (teacherUser == null) {
            throw new SchoolException(ResultEnum.USERNAME_ERROR);
        }

        //判断密码是否正确
        if (teacherUser.getTeaPassword().equals(password)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", username);
            return ResultVOUtil.success(map);
        } else {
            throw new SchoolException(ResultEnum.PASSWORD_ERROR);
        }
    }


    /**
     * 教师课程列表
     * @param teaId
     * @return
     */
    @GetMapping("/lessons/{teaId}")
    public ResultVO<List<LessonInfo>> lessons(@PathVariable("teaId") String teaId) {
        return ResultVOUtil.success(lessonService.getByTeaId(teaId));
    }

    /**
     * 教师密码修改
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    @PostMapping("/password")
    public ResultVO changePassword(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("newPassword") String newPassword) {

        TeacherUser teacherUser = teacherService.get(username);
        if (teacherUser.getTeaPassword().equals(password)) {
            teacherUser.setTeaPassword(newPassword);
            teacherService.save(teacherUser);
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.OLD_USERNAME_ERROR);
        }
    }

    /**
     * 班级详细信息
     * @param classId
     * @return
     */
    @GetMapping("/information/{classId}")
    public ResultVO<TeacherVO> teacherInfo(@PathVariable("classId") String classId) {
        TeacherUser teacherUser = teacherService.findByClassId(classId);
        TeacherVO teacherVO = new TeacherVO();
        if (teacherUser == null) {
            return ResultVOUtil.success(teacherVO);
        }
        BeanUtils.copyProperties(teacherUser, teacherVO);

        String majorId = teacherUser.getMajorId();
        String instituteId = teacherUser.getInstituteId();

        //获得major的名称
        if (majorId != null) {
            MajorInfo majorInfo = instituteMajorService.findMajor(majorId);
            if (majorInfo != null) {
                teacherVO.setMajorName(majorInfo.getMajorName());
            }
        }
        //获得institute名称
        if (instituteId != null) {
            InstituteInfo instituteInfo = instituteMajorService.findInstitute(instituteId);
            if (instituteInfo != null) {
                teacherVO.setInstituteName(instituteInfo.getInstituteName());
            }
        }

        return ResultVOUtil.success(teacherVO);
    }

    /**
     * 教师详细信息
     * @param teaId
     * @return
     */
    @GetMapping("/detail/{teaId}")
    public ResultVO<TeacherVO> studentDetail(@PathVariable("teaId") String teaId) {
        TeacherUser teacherUser = teacherService.get(teaId);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacherUser, teacherVO);

        String majorId = teacherUser.getMajorId();
        String instituteId = teacherUser.getInstituteId();

        //获得major的名称
        if (majorId != null) {
            MajorInfo majorInfo = instituteMajorService.findMajor(majorId);
            if (majorInfo != null) {
                teacherVO.setMajorName(majorInfo.getMajorName());
            }
        }
        //获得institute名称
        if (instituteId != null) {
            InstituteInfo instituteInfo = instituteMajorService.findInstitute(instituteId);
            if (instituteInfo != null) {
                teacherVO.setInstituteName(instituteInfo.getInstituteName());
            }
        }

        return ResultVOUtil.success(teacherVO);
    }

    /**
     * 班级select选项
     * @param classId
     * @return
     */
    @GetMapping("/list/{classId}")
    public ResultVO<List<ParamVO>> findByClassId(@PathVariable("classId") String classId) {

        TeacherUser teacherUser = teacherService.findByClassId(classId);
        ParamVO paramVO = new ParamVO();
        List<ParamVO> paramVOList = new ArrayList<>();
        if (teacherUser != null) {
            paramVO.setValue(teacherUser.getTeaId());
            paramVO.setLabel(teacherUser.getTeaName());
            paramVOList.add(paramVO);
        }
        return ResultVOUtil.success(paramVOList);
    }

    @GetMapping("/all")
    public ResultVO<List<TeacherVO>> findAll() {
        List<TeacherVO> teacherVOList = teacherService.findAll()
                .stream().map(e -> {
                    TeacherVO teacherVO = new TeacherVO();
                    BeanUtils.copyProperties(e, teacherVO);
                    return teacherVO;
                }).collect(Collectors.toList());
        return ResultVOUtil.success(teacherVOList);
    }

    @PostMapping("/deleteList")
    public ResultVO deleteList(@RequestParam("teaIdList") List<String> teaIdList) {
        teacherService.deleteList(teaIdList);
        return ResultVOUtil.success();
    }

    @DeleteMapping("/delete/{teaId}")
    public ResultVO delete(@PathVariable("teaId") String teaId) {
        teacherService.delete(teaId);
        return ResultVOUtil.success();
    }

    @GetMapping("/reset/{teaId}")
    public ResultVO resetPassword(@PathVariable("teaId") String teaId) {
        TeacherUser teacherUser = teacherService.get(teaId);
        if (teacherUser == null) {
            throw new SchoolException(ResultEnum.TEACHER_NOT_FIND);
        }
        teacherUser.setTeaPassword(TeacherConstant.defaultPassword);
        teacherService.save(teacherUser);
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO updateTeacher(@Valid TeacherForm teacherForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        TeacherUser teacherUser = teacherService.get(teacherForm.getTeaId());
        if (teacherUser == null) {
            throw new SchoolException(ResultEnum.TEACHER_NOT_FIND);
        }
        BeanUtils.copyProperties(teacherForm, teacherUser);
        teacherService.save(teacherUser);

        return ResultVOUtil.success();
    }

    @PostMapping("/create")
    public ResultVO createTeacher(@Valid TeacherForm teacherForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        String teaId = TeacherConstant.prefix + teacherForm.getInstituteId();
        String teaNum = FormatUtil.FormatFour(
                teacherService.instituteTeacherAmount(teaId) + 1);
        teaId = teaId + teaNum;
        if (teacherService.get(teaId) != null) {
            throw new SchoolException(ResultEnum.TEACHER_ID_ERROR);
        }

        TeacherUser teacherUser = new TeacherUser();
        BeanUtils.copyProperties(teacherForm, teacherUser);
        teacherUser.setTeaPassword(TeacherConstant.defaultPassword);
        teacherUser.setTeaNum(teaNum);
        teacherUser.setTeaId(teaId);
        teacherService.save(teacherUser);

        return ResultVOUtil.success();
    }
}
