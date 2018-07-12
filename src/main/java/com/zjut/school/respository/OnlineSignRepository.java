package com.zjut.school.respository;

import com.zjut.school.dataobject.OnlineSign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/27 18:09
 */
public interface OnlineSignRepository extends JpaRepository<OnlineSign, String> {

    OnlineSign findByLessonIdAndCreateTime(String lessonId, Date createTime);
}
