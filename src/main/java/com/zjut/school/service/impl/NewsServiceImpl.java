package com.zjut.school.service.impl;

import com.zjut.school.dataobject.NewsInfo;
import com.zjut.school.respository.NewsInfoRepository;
import com.zjut.school.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/25 15:39
 */

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsInfoRepository newsInfoRepository;

    @Override
    public List<NewsInfo> findAllNews() {
        return newsInfoRepository.findAll();
    }

    @Override
    public NewsInfo findOneNews(Integer newsId) {
        return newsInfoRepository.findOne(newsId);
    }

    @Override
    @Transactional
    public void deleteList(List<Integer> newsIdList) {
        for (int newsId : newsIdList) {
            newsInfoRepository.delete(newsId);
        }
    }

    @Override
    @Transactional
    public void create(NewsInfo newsInfo) {
        newsInfoRepository.save(newsInfo);
    }

    @Override
    @Transactional
    public void delete(Integer newsId) {
        newsInfoRepository.delete(newsId);
    }
}
