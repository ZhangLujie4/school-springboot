package com.zjut.school.service;

import com.zjut.school.dataobject.NewsInfo;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/25 15:34
 */
public interface NewsService {

    List<NewsInfo> findAllNews();

    NewsInfo findOneNews(Integer newsId);

    void create(NewsInfo newsInfo);

    void delete(Integer newsId);

    void deleteList(List<Integer> newsIdList);
}
