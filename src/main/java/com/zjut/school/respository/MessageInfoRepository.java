package com.zjut.school.respository;

import com.zjut.school.dataobject.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:13
 */
public interface MessageInfoRepository extends JpaRepository<MessageInfo, Integer> {

    /**
     * 已发送
     * @param sender
     * @return
     */
    List<MessageInfo> findBySender(String sender);


    /**
     * 收件箱
     * @param receiver
     * @return
     */
    List<MessageInfo> findByReceiver(String receiver);

    /**
     * 找到群发message
     * @param receiver
     * @param classId
     * @return
     */
    List<MessageInfo> findByReceiverAndClassId(String receiver, String classId);
}
