package com.zjut.school.controller;

import com.zjut.school.VO.ResultVO;
import com.zjut.school.dataobject.AdminUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.service.AdminService;
import com.zjut.school.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 张璐杰
 * 2018/4/16 15:10
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResultVO<Map<String, String>> login(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {

        AdminUser adminUser = adminService.get(username);

        if (adminUser == null) {
            throw new SchoolException(ResultEnum.USERNAME_ERROR);
        }

        if (adminUser.getPassword().equals(password)) {
            Map<String, String> map = new HashMap<>();
            map.put("id", username);
            return ResultVOUtil.success(map);
        } else {
            throw new SchoolException(ResultEnum.PASSWORD_ERROR);
        }
    }

    /**
     * 管理员密码修改
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    @PostMapping("/password")
    public ResultVO save(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("newPassword") String newPassword) {

        AdminUser adminUser = adminService.get(username);

        if (adminUser.getPassword().equals(password)) {
            adminUser.setPassword(newPassword);
            adminService.save(adminUser);
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.OLD_USERNAME_ERROR);
        }
    }
}
