package com.zjut.school.VO;

import lombok.Data;

/**
 * 结果呈现json
 * Created by 张璐杰
 * 2018/4/16 9:19
 */
@Data
public class ResultVO<T> {

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体内容
    private T data;
}
