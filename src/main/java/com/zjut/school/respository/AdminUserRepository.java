package com.zjut.school.respository;

import com.zjut.school.dataobject.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 张璐杰
 * 2018/4/15 3:27
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
