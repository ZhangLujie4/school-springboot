package com.zjut.school.utils;

import com.zjut.school.VO.ResultVO;
import com.zjut.school.enums.ResultEnum;

/**
 * Created by 张璐杰
 * 2018/4/16 11:27
 */
public class ResultVOUtil {

//    public static ResultVO success(ResultEnum resultEnum, Object object) {
//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(resultEnum.getCode());
//        resultVO.setMsg(resultEnum.getMessage());
//        resultVO.setData(object);
//        return resultVO;
//    }

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
