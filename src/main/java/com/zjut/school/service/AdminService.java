package com.zjut.school.service;

import com.zjut.school.dataobject.AdminUser;

/**
 * Created by 张璐杰
 * 2018/4/16 10:31
 */
public interface AdminService {

    AdminUser get(String adminId);

    void save(AdminUser adminUser);
}
