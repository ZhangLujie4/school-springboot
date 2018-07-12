package com.zjut.school.utils;

import com.zjut.school.VO.MessageVO;

import java.util.Comparator;

/**
 * Created by 张璐杰
 * 2018/5/20 17:57
 */
public class msgComparator implements Comparator<MessageVO> {

    @Override
    public int compare(MessageVO o1, MessageVO o2) {
        return o2.getMessageId() - o1.getMessageId();
    }
}
