package com.zjut.school.VO;

import lombok.Data;

import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/26 1:20
 */

@Data
public class NewsVO {

    private Integer newsId;

    private String newsTitle;

    private Date createTime;
}
