package com.zjut.school.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjut.school.VO.NewsVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.dataobject.NewsInfo;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.service.NewsService;
import com.zjut.school.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/25 15:43
 */

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 所有新闻
     * @return
     */
    @GetMapping("/all")
    public ResultVO<List<NewsVO>> findAll() {
        List<NewsInfo> newsInfoList = newsService.findAllNews();
        if (CollectionUtils.isEmpty(newsInfoList)) {
            throw new SchoolException(ResultEnum.NEWS_EMPTY);
        }
        List<NewsVO> newsVOList = new ArrayList<>();
        for (NewsInfo newsInfo : newsInfoList) {
            NewsVO newsVO = new NewsVO();
            BeanUtils.copyProperties(newsInfo, newsVO);
            newsVOList.add(newsVO);
        }

        return ResultVOUtil.success(newsVOList);
    }

    /**
     * 新闻详情
     * @param newsId
     * @return
     */
    @GetMapping("/detail/{newsId}")
    public ResultVO<NewsInfo> findOne(@PathVariable("newsId") Integer newsId) {
        NewsInfo newsInfo = newsService.findOneNews(newsId);
        if (newsInfo == null) {
            throw new SchoolException(ResultEnum.NEWS_DETAIL_ERROR);
        }

        return ResultVOUtil.success(newsInfo);
    }

    /**
     * 创建新闻
     * @param newsTitle
     * @param newsContent
     */
    @PostMapping("/create")
    public ResultVO create(@RequestParam("newsTitle") String newsTitle,
                       @RequestParam("newsContent") String newsContent) {
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setNewsTitle(newsTitle);
        newsInfo.setNewsContent(newsContent);

        newsService.create(newsInfo);
        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO update(@RequestParam("newsId") Integer newsId,
                       @RequestParam("newsTitle") String newsTitle,
                       @RequestParam("newsContent") String newsContent) {
        NewsInfo newsInfo = newsService.findOneNews(newsId);
        newsInfo.setNewsTitle(newsTitle);
        newsInfo.setNewsContent(newsContent);

        newsService.create(newsInfo);
        return ResultVOUtil.success();
    }

    /**
     * 删除一条新闻
     * @param newsId
     * @return
     */
    @DeleteMapping("/delete/{newsId}")
    public ResultVO deleteOne(@PathVariable("newsId") Integer newsId) {
        newsService.delete(newsId);
        return ResultVOUtil.success();
    }

    @PostMapping("/deleteList")
    public ResultVO deleteList(@RequestParam("newsIdList") List<Integer> newsIdList) {
        newsService.deleteList(newsIdList);
        return ResultVOUtil.success();
    }
}
