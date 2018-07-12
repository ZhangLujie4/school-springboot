package com.zjut.school.respository;

import com.zjut.school.dataobject.SignIn;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.dsig.SignedInfo;
import java.util.Date;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:16
 */
public interface SignInRepository extends JpaRepository<SignIn, String> {

    /**
     * 查看某天某节课的点到情况
     * @param lessonId
     * @param date
     * @return
     */
    List<SignIn> findByLessonIdAndSignDateAndIsSign(String lessonId, Date date, boolean isSign);

    /**
     * 查看学生自己的点到情况
     * @param stuId
     * @return
     */
    List<SignIn> findByStuId(String stuId);

    List<SignIn> findByLessonNum(String lessonNum);
}
