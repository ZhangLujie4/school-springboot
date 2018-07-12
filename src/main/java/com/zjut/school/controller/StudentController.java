package com.zjut.school.controller;

import com.zjut.school.VO.ParamVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.VO.StudentVO;
import com.zjut.school.constant.DataLocalConstant;
import com.zjut.school.constant.StudentConstant;
import com.zjut.school.dataobject.ClassInfo;
import com.zjut.school.dataobject.InstituteInfo;
import com.zjut.school.dataobject.MajorInfo;
import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.form.StudentForm;
import com.zjut.school.service.InstituteMajorService;
import com.zjut.school.service.StudentService;
import com.zjut.school.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 13:58
 */

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstituteMajorService instituteMajorService;

    //教师用户login
    @PostMapping("/login")
    public ResultVO<Map<String, String>> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {

        StudentUser studentUser = studentService.get(username);

        //用户名不存在
        if (studentUser == null) {
            throw new SchoolException(ResultEnum.USERNAME_ERROR);
        }
        //判断密码是否正确
        if (studentUser.getStuPassword().equals(password)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", username);
            //返回数据
            return ResultVOUtil.success(map);
        } else {
            throw new SchoolException(ResultEnum.PASSWORD_ERROR);
        }
    }

    /**
     * 学生信息
     * @param stuId
     * @return
     */
    @GetMapping("/detail/{stuId}")
    public ResultVO<StudentVO> studentDetail(@PathVariable("stuId") String stuId) {
        StudentUser studentUser = studentService.get(stuId);
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(studentUser, studentVO);

        String majorId = studentUser.getMajorId();
        String instituteId = studentUser.getInstituteId();

        //获得major的名称
        if (majorId != null) {
            MajorInfo majorInfo = instituteMajorService.findMajor(majorId);
            if (majorInfo != null) {
                studentVO.setMajorName(majorInfo.getMajorName());
            }
        }
        //获得institute名称
        if (instituteId != null) {
            InstituteInfo instituteInfo = instituteMajorService.findInstitute(instituteId);
            if (instituteInfo != null) {
                studentVO.setInstituteName(instituteInfo.getInstituteName());
            }
        }
        return ResultVOUtil.success(studentVO);
    }

    /**
     * 学生密码修改
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    @PostMapping("/password")
    public ResultVO save(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("newPassword") String newPassword) {

        StudentUser studentUser = studentService.get(username);

        if (studentUser.getStuPassword().equals(password)) {
            studentUser.setStuPassword(newPassword);
            studentService.save(studentUser);
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.OLD_USERNAME_ERROR);
        }
    }

    /**
     * 学生课程列表用于select
     * @param classId
     * @return
     */
    @GetMapping("/list/{classId}")
    public ResultVO<List<ParamVO>> findByClassId(@PathVariable("classId") String classId) {

        List<ParamVO> paramVOList = studentService.findByClassId(classId)
                .stream().map(e-> {
                    ParamVO paramVO = new ParamVO();
                    paramVO.setValue(e.getStuId());
                    paramVO.setLabel(e.getStuName());
                    return paramVO;
                }).collect(Collectors.toList());

        log.info("{}", paramVOList);
        return ResultVOUtil.success(paramVOList);
    }

    @GetMapping("/all")
    public ResultVO<List<StudentVO>> findAll() {
        List<StudentVO> studentVOList = studentService.findAll()
                .stream().map(e -> {
                    StudentVO studentVO = new StudentVO();
                    BeanUtils.copyProperties(e, studentVO);
                    return studentVO;
                }).collect(Collectors.toList());
        return ResultVOUtil.success(studentVOList);
    }

    @PostMapping("/deleteList")
    public ResultVO deleteList(@RequestParam("stuIdList") List<String> stuIdList) {
       studentService.deleteList(stuIdList);
       return ResultVOUtil.success();
    }

    @DeleteMapping("/delete/{stuId}")
    public ResultVO delete(@PathVariable("stuId") String stuId) {
        studentService.delete(stuId);
        return ResultVOUtil.success();
    }

    @GetMapping("/reset/{stuId}")
    public ResultVO resetPassword(@PathVariable("stuId") String stuId) {
        StudentUser studentUser = studentService.get(stuId);
        if (studentUser == null) {
            throw new SchoolException(ResultEnum.STUDENT_NOT_FIND);
        }
        studentUser.setStuPassword(StudentConstant.defaultPassword);
        studentService.save(studentUser);
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO updateStudent(@Valid StudentForm studentForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        StudentUser studentUser = studentService.get(studentForm.getStuId());
        if (studentUser == null) {
            throw new SchoolException(ResultEnum.STUDENT_NOT_FIND);
        }
        BeanUtils.copyProperties(studentForm, studentUser);
        studentService.save(studentUser);

        return ResultVOUtil.success();
    }

    @PostMapping("/create")
    public ResultVO createStudent(@Valid StudentForm studentForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        String stuId = studentForm.getStuYear() + studentForm.getInstituteId() + studentForm.getClassNum();
        String stuNum = FormatUtil.FormatTwo(
                studentService.classStuIdAmount(stuId) + 1);
        stuId = stuId + stuNum;
        if (studentService.get(stuId) != null) {
            throw new SchoolException(ResultEnum.STUDENT_ID_ERROR);
        }

        StudentUser studentUser = new StudentUser();
        BeanUtils.copyProperties(studentForm, studentUser);
        studentUser.setStuPassword(StudentConstant.defaultPassword);
        studentUser.setStuNum(stuNum);
        studentUser.setStuId(stuId);
        studentService.save(studentUser);
        return ResultVOUtil.success();
    }
}
