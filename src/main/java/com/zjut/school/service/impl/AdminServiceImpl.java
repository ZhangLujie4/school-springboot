package com.zjut.school.service.impl;

import com.zjut.school.dataobject.AdminUser;
import com.zjut.school.respository.AdminUserRepository;
import com.zjut.school.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 张璐杰
 * 2018/4/16 15:07
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public AdminUser get(String adminId) {
        return adminUserRepository.findOne(adminId);
    }

    @Override
    public void save(AdminUser adminUser) {
        adminUserRepository.save(adminUser);
    }
}
