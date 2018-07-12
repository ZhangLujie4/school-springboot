package com.zjut.school.respository;

import com.zjut.school.dataobject.NewsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 张璐杰
 * 2018/4/15 10:15
 */
public interface NewsInfoRepository extends JpaRepository<NewsInfo, Integer> {
}
